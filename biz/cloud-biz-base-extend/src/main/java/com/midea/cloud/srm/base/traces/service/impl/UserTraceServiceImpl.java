package com.midea.cloud.srm.base.traces.service.impl;

import com.midea.cloud.common.utils.redis.RedisLockUtil;
import com.midea.cloud.common.utils.redis.RedisUtil;
import com.midea.cloud.srm.base.traces.service.UserTraceService;
import com.midea.cloud.srm.feign.log.LogClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/6/8
 *  修改内容:
 * </pre>
 */
@Service
@Slf4j
public class UserTraceServiceImpl implements UserTraceService {
    /**
     * 实现原理: 浏览器每次打开一个tab页, vue就会创建一个实例, 进行初始化
     * 初始化的时候请求一下后台, 记录该用户会话开始
     * <p>
     * 1. 一个用户账号可能会同时打开多个浏览器
     * 解决方案:
     * 情况一: 用户第一次打开会话, 保存用户会话基本信息, 并且把用户ID作为key存到redis, 设置有效时间为2分钟
     * 为了保证会话的时效性, 前端每1分钟会请求一次后台把该key的有效时间重新设为2分钟(当用户关闭浏览器, 2分钟后该key就会失效)
     * 情况二: 同一个账号重复打开会话, 通过用户id去去找key的值, 有则不处理, 无责新开一个会话
     * <p>
     * 2. 会话结束方案: 定时任务在表里找会话没结束的用户账号去缓存查, 如果找不到了, 表明会话结束
     */
    @Resource
    RedisUtil redisUtil;
    @Resource
    LogClient logClient;
    @Resource
    RedisLockUtil redisLockUtil;

    @Override
    public void startRecordingSession() {
//        // 获取用户名
//        String userName = AppUserUtil.getUserName();
//        String redisKey = RedisKey.USER_TRACE_USERNAME + userName;
//        // 判断是否已存在会话
//        long num = redisUtil.sadd(redisKey, RedisCacheTime.CACHE_EXP_TWO_MINUTE, redisKey);
//        if(num>0){
//            // 表示第一次进入会话, 设置新会话信息
//            UserTrace userTrace = new UserTrace();
//            userTrace.setTraceId(IdGenrator.generate());
//            userTrace.setNowDate(LocalDate.now());
//            userTrace.setUsername(userName);
//            userTrace.setLoginDate(new Date());
//            // 异步
//            CompletableFuture.runAsync(() -> {
//                try {
//                    // 获取锁
//                    redisLockUtil.lock(userTrace.getUsername(),RedisCacheTime.CACHE_EXP_TWO_MINUTE);
//                    logClient.add(userTrace);
//                }finally {
//                    redisLockUtil.unlock(userTrace.getUsername());
//                }
//            });
//        }else {
//            // 设置缓存时间为2分钟
//            redisUtil.expire(redisKey,RedisCacheTime.CACHE_EXP_TWO_MINUTE);
//        }
    }

    @Override
    public void sessionEnd() {
//        log.info("开始扫描会话还没结束的用户进行关闭");
//        // 查找会话没结束的user
//        List<String> noEndUser = logClient.queryNoEndUser();
//        ArrayList<String> userList = new ArrayList<>();
//        if(CollectionUtils.isNotEmpty(noEndUser)){
//            noEndUser.forEach(userName->{
//                String redisKey = RedisKey.USER_TRACE_USERNAME + userName;
//                // 判断key是否存在
//                if(!redisUtil.exists(redisKey)){
//                    userList.add(userName);
//                }
//            });
//        }
//        if(CollectionUtils.isNotEmpty(userList)){
//            // 异步
//            CompletableFuture.runAsync(() -> {
//                logClient.modify(userList);
//            });
//        }
    }
}
