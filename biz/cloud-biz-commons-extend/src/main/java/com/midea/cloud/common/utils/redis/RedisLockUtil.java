package com.midea.cloud.common.utils.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * 分布式锁
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/6/9
 *  修改内容:
 * </pre>
 */
@Component
public class RedisLockUtil {

    @Resource
    protected RedisTemplate<String, Object> redisTemplate;

    // 设置当前线程变量
    private ThreadLocal<String> threadKeyId = ThreadLocal.withInitial(() -> UUID.randomUUID().toString());

    /**
     * redis锁
     *
     * @param lock   锁名
     * @param seconds 锁的最大有效时间(秒)
     */
    public void lock(String lock, int seconds) {
        // 锁名
        String keyName = getLockName(lock);
        while (true) {
            // 检查是否存在死锁,死锁则删除key
            checkRedisKey(keyName);
            // 设置锁, 返回成功个数
            long lockNum = sSet(keyName, keyName,threadKeyId.get());
            // 判断key是否存在
            if (lockNum == 2) {
                // 设置锁的最大有效时间
                expire(keyName, seconds);
                break;
            } else {
                // 判断是否重入锁
                if (sHasKey(keyName, threadKeyId.get())) {
                    break;
                }
                // 短暂休眠，nano避免出现活锁
                try {
                    Thread.sleep(500, 500);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    /**
     * 检查key是否死锁
     * @param keyName
     */
    public void checkRedisKey(String keyName) {
        // 无过期时间返回"-1", key不存在返回"-2", 其他返回有效时间(毫秒)
        Long expire = redisTemplate.getExpire(keyName, TimeUnit.MICROSECONDS);
        if(null != expire && expire.compareTo(-1L) == 0){
            redisTemplate.delete(keyName);
        }
    }

    /**
     * 释放锁
     *
     * @param lock
     */
    public void unlock(String lock) {
        // 锁名
        String keyName = getLockName(lock);
        // 删掉锁
        redisTemplate.delete(keyName);
    }

    /**
     * 获取锁的名字
     *
     * @param key
     * @return
     */
    public String getLockName(String key) {
        if (null != key) {
            return "REDIS_LOCK_" + key;
        } else {
            return "DEFAULT_LOCK";
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * .
     * 指定缓存失效时间
     *
     * @param key     键
     * @param seconds 时间(秒)
     * @return
     */
    public boolean expire(String key, long seconds) {
        try {
            if (seconds > 0) {
                redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
