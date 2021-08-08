package com.midea.cloud.common.annotation;

import com.alibaba.fastjson.JSON;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.utils.ExceptionUtil;
import com.midea.cloud.common.utils.redis.RedisUtil;
import com.midea.cloud.component.context.container.SpringContextHolder;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.base.rediscache.entity.RedisCacheMan;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <pre>
 * 缓存代理类
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 09:24:20
 *  修改内容:
 * </pre>
 */
@Aspect
@Slf4j
public class CacheAop {

    private static BaseClient baseClient;

    private static RedisUtil redisUtil;

    @Around(value = "@annotation(com.midea.cloud.common.annotation.CacheClear)")
    public Object cacheClear(ProceedingJoinPoint pjp) throws Throwable {
        log.info("----------------缓存清除AOP开始--------------------");
        // 执行业务
        Object obj = pjp.proceed();
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        // 获取注解
        Method method = signature.getMethod();
        CacheClear cacheClear = method.getAnnotation(CacheClear.class);
        // 如果没有注解则直接返回
        if(null == cacheClear) return obj;
        // 缓存key
        String[] keyNames = cacheClear.keyName();
        if(ObjectUtils.isEmpty(keyNames)) return obj;
        try {
            RedisUtil redisUtil = getRedisUtil();
            Arrays.stream(keyNames).forEach(redisKey -> {
                log.info("清除缓存的KEY:"+redisKey);
                // 删除缓存
                redisUtil.deleteByPattern(redisKey+"*");
            });
        } catch (Exception e) {
            String errorMsg = ExceptionUtil.getErrorMsg(e);
            log.error("清除缓存报错:{}",errorMsg);
        }

        log.info("----------------缓存清除AOP结束--------------------");
        return obj;
    }

    /**
     * 拼接redisKey
     * @param args
     * @param keyName
     * @param paramIndex
     * @param redisKey
     * @return
     */
    public boolean getRedisKey(Object[] args, String keyName, int[] paramIndex, AtomicReference<String> redisKey, boolean i18n) {
        boolean flag = true;
        if (!ObjectUtils.isEmpty(args)) {
            if(!ObjectUtils.isEmpty(paramIndex)){
                /**
                 * paramIndex的元素不能大于args的size
                 */
                Object[] argsNew = new Object[paramIndex.length];
                try {
                    for(int i = 0;i < paramIndex.length;i++){
                        int index = paramIndex[0];
                        Assert.isTrue(index < args.length,"paramIndex存在参数下标大于方法参数个数");
                        argsNew[i] = args[index];
                    }
                    flag = setRedisKey(argsNew, redisKey, i18n);
                } catch (Exception e) {
                    String errorMsg = ExceptionUtil.getErrorMsg(e);
                    log.error("拼接自定义参数序列化报错:{}",errorMsg);
                    flag = false;
                }
            }else {
                flag = setRedisKey(args, redisKey, i18n);
            }
        }
        return flag;
    }

    public boolean setRedisKey(Object[] args, AtomicReference<String> redisKey, boolean i18n) {
        boolean flag = true;
        try {
            String paramJson = JSON.toJSONString(args);
            log.info("方法参数:"+paramJson);
            StringBuffer key = new StringBuffer().append(redisKey.get());
            if (i18n) {
                String localeKey = LocaleHandler.getLocaleKey();
                key.append(":").append(localeKey);
            }
            key.append(":").append(paramJson);
            redisKey.set(key.toString());
        } catch (Exception e) {
            String errorMsg = ExceptionUtil.getErrorMsg(e);
            log.error("方法参数转JSON异常,信息:{}",errorMsg);
            flag = false;
        }
        return flag;
    }

    @Around(value = "@annotation(com.midea.cloud.common.annotation.CacheData)")
    public Object aroundCacheData(ProceedingJoinPoint pjp) throws Throwable {
        log.info("----------------缓存AOP执行开始--------------------");
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        // 获取注解
        Method method = signature.getMethod();
        CacheData cacheData = method.getAnnotation(CacheData.class);
        // 如果没有注解则直接返回
        if(null == cacheData) return pjp.proceed();
        // 缓存key
        String keyName = cacheData.keyName();
        log.info("传参缓存KEY:"+keyName);
        // 缓存时间
        long cacheTime = cacheData.cacheTime();
        log.info("传参缓存时间:"+cacheTime+"秒");
        // 参数
        Object[] args = pjp.getArgs();
        // 获取参数下标
        int[] paramIndex = cacheData.paramIndex();
        // 拼装缓存KEY
        AtomicReference<String> redisKey = new AtomicReference<>(keyName);
        if(!getRedisKey(args, keyName, paramIndex, redisKey, cacheData.i18n())){
            return pjp.proceed();
        }

        Object obj = null;
        try {
            RedisCacheMan redisCacheMan = new RedisCacheMan();
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            redisCacheMan.setInterfaceUrl(request.getRequestURI());
            redisCacheMan.setIfOpen(YesOrNo.YES.getValue());
            redisCacheMan.setCacheKey(redisKey.get());
            redisCacheMan.setInterfaceName(cacheData.interfaceName());
            redisCacheMan.setRemark(cacheData.remark());
            // 判断缓存是否开启
            if (ifOpen(redisCacheMan))
            {
                RedisUtil redisUtil = getRedisUtil();
                // 从缓存中获取数据
                obj = redisUtil.get(redisKey.get());
                if (null == obj) {
                    obj = pjp.proceed();
                    redisUtil.set(redisKey.get(), obj, cacheTime);
                }
            }else {
                obj = pjp.proceed();
            }
        } catch (SerializationException e){
            log.error("缓存执行报错: 方法返回对象需实现接口Serialization!");
            obj = pjp.proceed();
        } catch (Exception e) {
            String errorMsg = ExceptionUtil.getErrorMsg(e);
            log.error("缓存执行报错:{}",errorMsg);
            obj = pjp.proceed();
        }
        log.info("----------------缓存AOP执行结束--------------------");
        return obj;
    }

    /**
     * 检查缓存是否开启
     * @return
     */
    public static boolean ifOpen(RedisCacheMan redisCacheMan){
        BaseClient baseClient = getBaseClient();
        return baseClient.checkCacheIfOpenNew(redisCacheMan);
    }

    public static synchronized BaseClient getBaseClient(){
        if(null == baseClient){
            baseClient = SpringContextHolder.getBean(BaseClient.class);
        }
        return baseClient;
    }

    public static synchronized RedisUtil getRedisUtil(){
        if(null == redisUtil){
            redisUtil = SpringContextHolder.getBean(RedisUtil.class);
        }
        return redisUtil;
    }
}
