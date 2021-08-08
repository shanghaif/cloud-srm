package com.midea.cloud.common.annotation;

import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.model.annonations.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.ObjectUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <pre>
 * 检查参数非空AOP
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
public class CheckParamAop {
    @Around("@within(com.midea.cloud.common.annotation.CheckParam) || @annotation(com.midea.cloud.common.annotation.CheckParam)")
    public Object CheckParam(ProceedingJoinPoint pjp) throws Throwable {
        try {
            MethodSignature signature = (MethodSignature) pjp.getSignature();
            // 方法参数注解类型
            Annotation[][] parameterAnnotations = signature.getMethod().getParameterAnnotations();
            // 方法参数的类型
            Class<?>[] parameterTypes = signature.getMethod().getParameterTypes();
            // 获取方法参数
            Object[] args = pjp.getArgs();
            if(!ObjectUtils.isEmpty(args)){
                // 遍历参数
                AtomicInteger index = new AtomicInteger(0);
                Arrays.stream(args).forEach(o -> {
                    int indexNo = index.getAndAdd(1);
                    /**
                     * 检查方法参数非空
                     */
                    Annotation[] parameterAnnotation = parameterAnnotations[indexNo];
                    if(!ObjectUtils.isEmpty(parameterAnnotation)){
                        Arrays.stream(parameterAnnotation).forEach(annotation -> {
                            if(annotation instanceof NotNull){
                                NotNull notNull = (NotNull)annotation;
                                // 注解信息
                                String message = notNull.value();
                                // 通过工具类获取多语言信息
                                String localeMsg = LocaleHandler.getLocaleMsg(message);
                                // 检查参数非空
                                Optional.ofNullable(o).
                                        filter(o1 -> !ObjectUtils.isEmpty(o1)).
                                        orElseThrow(()->new BaseException(localeMsg));
                            }
                        });
                    }

                    /**
                     * 检查方法参数属性非空
                     */
                    Class<?> parameterType = parameterTypes[indexNo];
                    Field[] fields = parameterType.getDeclaredFields();
                    if(!ObjectUtils.isEmpty(fields)){
                        // 遍历属性
                        Arrays.stream(fields).forEach(field -> {
                            NotNull annotation = field.getAnnotation(NotNull.class);
                            if(null != annotation){
                                Object value = null;
                                // 注解信息
                                String message = annotation.value();
                                // 通过工具类获取多语言信息
                                String localeMsg = LocaleHandler.getLocaleMsg(message);
                                Optional.ofNullable(o).orElseThrow(()->new BaseException(localeMsg));
                                try {
                                    field.setAccessible(true);
                                    value = field.get(o);
                                } catch (Exception e) {
                                    log.error("获取属性值报错"+e.getMessage());
                                    log.error("获取属性值报错"+e);
                                }
                                // value为空时报错
                                Optional.ofNullable(value).
                                        filter(o1 -> !ObjectUtils.isEmpty(o1)).
                                        orElseThrow(()->new BaseException(localeMsg));
                            }
                        });
                    }
                });
            }
        } catch (BaseException e) {
            throw e;
        } catch (Exception e){
            log.error("检查参数aop报错:"+e.getMessage());
            log.error("检查参数aop报错:"+e);
        }
        return pjp.proceed();
    }
}
