package com.midea.cloud.common.utils.redis;

import com.midea.cloud.common.constants.RedisCacheTime;
import com.midea.cloud.common.utils.NamedThreadFactory;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * <pre>
 * redis工具类
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
@Component
public class RedisUtil  {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private volatile DefaultRedisScript<Boolean> lockScript;

    private volatile DefaultRedisScript<Long> upLockScript;

    private volatile DefaultRedisScript<Boolean> extendScript;
    /**
     * 默认锁的时间,单位s
     */
    private final static long INTERVAL_CHECK_EXPIRE = 30;
    private final String PREFIX = "redisLock:";
    private volatile HashedWheelTimer wheelTimer;

    private DefaultRedisScript getLockScript() {
        if (Objects.isNull(lockScript)) {
            synchronized (RedisUtil.class) {
                if (Objects.isNull(lockScript)) {
                    lockScript = new DefaultRedisScript();
                    lockScript.setResultType(Boolean.class);
                    lockScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("Lock.lua")));
                }
            }
        }
        return lockScript;
    }

    private DefaultRedisScript getUnLockScript() {
        if (Objects.isNull(upLockScript)) {
            synchronized (RedisUtil.class) {
                if (Objects.isNull(upLockScript)) {
                    upLockScript = new DefaultRedisScript();
                    upLockScript.setResultType(Long.class);
                    upLockScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("UnLock.lua")));
                }
            }
        }
        return upLockScript;
    }

    /**
     * 续约脚本初始化、以及初始化时间轮
     *
     * @return
     */
    private DefaultRedisScript getExtendScript() {
        if (Objects.isNull(extendScript)) {
            synchronized (RedisUtil.class) {
                if (Objects.isNull(extendScript)) {
                    /**
                     * 单个桶间隔为0.5s,总共有1024个桶
                     */
                    wheelTimer = new HashedWheelTimer(new NamedThreadFactory("watch-dog-thread-pool",true), 500, TimeUnit.MILLISECONDS, 1024);
                    extendScript = new DefaultRedisScript<>();
                    extendScript.setResultType(Boolean.class);
                    extendScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("Extend.lua")));
                }
            }
        }
        return extendScript;
    }

    /**
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
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key 获取剩余过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long ttl(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean exists(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 批量删除缓存
     * @param keys
     */
    public void del(Collection<String> keys){
        if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(keys)){
            redisTemplate.delete(keys);
        }
    }

    /**
     * 模糊匹配批量删除
     *
     * @param pattern 匹配的前缀
     */
    public void deleteByPattern(String pattern) {
        Set<String> keys = stringRedisTemplate.keys(pattern);
        if (!CollectionUtils.isEmpty(keys)) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 设置指定 key 的值
     *
     * @param key     键
     * @param value   值
     * @param seconds 时间(秒) seconds要大于0 如果seconds小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long seconds) {
        try {
            if (seconds == RedisCacheTime.CACHE_EXP_FOREVER) {
                redisTemplate.opsForValue().set(key, value);
                stringRedisTemplate.opsForValue().set(key,key);
            } else {
                redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
                stringRedisTemplate.opsForValue().set(key,key,seconds,TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取指定 key 的值
     *
     * @param key 键
     * @return 值
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return key == null ? null : (T) redisTemplate.opsForValue().get(key);
    }

    /**
     * 将 key 中储存的数字值递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public long incr(String key, long delta) {
        if (delta <= 0) {
            throw new IllegalArgumentException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 将 key 中储存的数字值递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public long decr(String key, long delta) {
        if (delta <= 0) {
            throw new IllegalArgumentException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    /**
     * 将哈希表 key 中的字段 field 的值设为 value
     *
     * @param key     键
     * @param field   字段
     * @param value   值
     * @param seconds 时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key, String field, Object value, long seconds) {
        try {
            redisTemplate.opsForHash().put(key, field, value);
            if (seconds != RedisCacheTime.CACHE_EXP_FOREVER) {
                expire(key, seconds);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中
     *
     * @param key     键
     * @param map     对应多个键值
     * @param seconds 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmset(String key, Map<String, Object> map, long seconds) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (seconds != RedisCacheTime.CACHE_EXP_FOREVER) {
                expire(key, seconds);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除一个或多个哈希表字段
     *
     * @param key   键
     * @param field 字段 可以多个
     */
    public void hdel(String key, Object... field) {
        redisTemplate.opsForHash().delete(key, field);
    }

    /**
     * 获取存储在哈希表中指定字段的值
     *
     * @param key   键
     * @param field 字段
     * @return 值
     */
    public <T> T hget(String key, String field) {
        return (T) redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 获取在哈希表中指定 key 的所有字段和值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }


    /**
     * 查看哈希表 key 中，指定的字段是否存在
     *
     * @param key   键
     * @param field 字段
     * @return true 存在 false不存在
     */
    public boolean hexists(String key, String field) {
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    /**
     * 获取哈希表中字段的数量
     *
     * @param key 键
     * @return 字段数量
     */
    public long hlen(String key) {
        try {
            return redisTemplate.opsForHash().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 向集合添加一个或多个成员
     *
     * @param key     键
     * @param seconds 时间(秒)
     * @param values  成员 可以是多个
     * @return 成功个数
     */
    public long sadd(String key, long seconds, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (seconds != RedisCacheTime.CACHE_EXP_FOREVER) {
                expire(key, seconds);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 移除集合中一个或多个成员
     *
     * @param key    键
     * @param values 成员 可以是多个
     * @return 移除的个数
     */
    public long srem(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 返回集合中的所有成员
     *
     * @param key 键
     * @return 成员列表
     */
    public <T> Set<T> smembers(String key) {
        try {
            return (Set<T>) redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断 member 元素是否是集合 key 的成员
     *
     * @param key    键
     * @param member 成员
     * @return true 存在 false不存在
     */
    public boolean sismember(String key, Object member) {
        try {
            return redisTemplate.opsForSet().isMember(key, member);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 获取集合的成员数
     *
     * @param key 键
     * @return 成员数
     */
    public long slen(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 在列表头部添加一个值
     *
     * @param key     键
     * @param value   值
     * @param seconds 时间(秒)
     * @return boolean
     */
    public boolean lpush(String key, Object value, long seconds) {
        try {
            redisTemplate.opsForList().leftPush(key, value);
            if (seconds != RedisCacheTime.CACHE_EXP_FOREVER) {
                expire(key, seconds);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 在列表头部添加多个值
     *
     * @param key     键
     * @param values  值
     * @param seconds 时间(秒)
     * @return boolean
     */
    public boolean lpush(String key, List<Object> values, long seconds) {
        try {
            redisTemplate.opsForList().leftPushAll(key, values);
            if (seconds != RedisCacheTime.CACHE_EXP_FOREVER) {
                expire(key, seconds);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 在列表尾部添加一个值
     *
     * @param key     键
     * @param value   值
     * @param seconds 时间(秒)
     * @return boolean
     */
    public boolean rpush(String key, Object value, long seconds) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (seconds != RedisCacheTime.CACHE_EXP_FOREVER) {
                expire(key, seconds);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 在列表尾部添加多个值
     *
     * @param key     键
     * @param values  值
     * @param seconds 时间(秒)
     * @return boolean
     */
    public boolean rpush(String key, List<Object> values, long seconds) {
        try {
            redisTemplate.opsForList().rightPushAll(key, values);
            if (seconds != RedisCacheTime.CACHE_EXP_FOREVER) {
                expire(key, seconds);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除列表元素
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lrem(String key, long count, Object value) {
        try {
            return redisTemplate.opsForList().remove(key, count, value);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 通过索引设置列表元素的值
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return boolean
     */
    public boolean lset(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取列表指定范围内的元素
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return 元素列表
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> lrange(String key, long start, long end) {
        try {
            return (List<T>) redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过索引获取列表中的元素
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lindex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取列表长度
     *
     * @param key 键
     * @return 列表长度
     */
    public long llen(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 向有序集合添加一个成员，或者更新已存在成员的分数
     *
     * @param key     键
     * @param seconds 时间(秒)
     * @param member  成员
     * @param score   分数
     * @return
     */
    public boolean zadd(String key, long seconds, Object member, double score) {
        try {
            boolean ret = redisTemplate.opsForZSet().add(key, member, score);
            if (seconds != RedisCacheTime.CACHE_EXP_FOREVER) {
                expire(key, seconds);
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除有序集合中的一个或多个成员
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long zrem(String key, Object... values) {
        try {
            return redisTemplate.opsForZSet().remove(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 通过索引区间返回有序集合成指定区间内的成员 分数从低到高
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return 成员集合
     */
    public Set<Object> zrange(String key, long start, long end) {
        try {
            return redisTemplate.opsForZSet().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过索引区间返回有序集合成指定区间内的成员 分数从高到低
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return 成员集合
     */
    public Set<Object> zrevrange(String key, long start, long end) {
        try {
            return redisTemplate.opsForZSet().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回有序集合中某个成员的分数值
     *
     * @param key    键
     * @param member 成员
     * @return 分数值
     */
    public double zscore(String key, Object member) {
        try {
            return redisTemplate.opsForZSet().score(key, member);
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    /**
     * 判断有序集合中某个成员是否存在
     *
     * @param key    键
     * @param member 成员
     * @return true 存在 false不存在
     */
    public boolean zexist(String key, Object member) {
        try {
            return null != redisTemplate.opsForZSet().score(key, member);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取有序集合的成员数
     *
     * @param key 键
     * @return 成员数
     */
    public long zlen(String key) {
        try {
            return redisTemplate.opsForZSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }


    /**
     * 秒为单位
     * @param key
     * @param expireSecond
     * @param awaitSecond
     * @param retryCount
     * @return
     */
    public Boolean tryLockInTime(String key, long expireSecond, long awaitSecond, long retryCount) {
        return tryLockInTime(key, expireSecond, TimeUnit.SECONDS, awaitSecond, TimeUnit.SECONDS, retryCount);
    }

    /**
     * 续约功能
     * @param key
     * @param awaitSecond
     * @param retryCount
     * @return
     */
    public Boolean tryLockInTime(String key, long awaitSecond, long retryCount) {
        return tryLockInTime(key, -1, TimeUnit.SECONDS, awaitSecond, TimeUnit.SECONDS, retryCount);
    }

    /**
     * 每次阻塞100ms
     *
     * @param key
     * @param time
     * @param unit
     * @param awaitTime
     * @param awaitUnit
     * @param retryCount
     * @return
     */
    public Boolean tryLockInTime(String key, long time, TimeUnit unit, long awaitTime, TimeUnit awaitUnit, long retryCount) {
        //每睡100ms起来获取
        return tryLockInTime(key, time, unit, awaitTime, awaitUnit, retryCount, 100, TimeUnit.MILLISECONDS);
    }
    /**
     * 超时|续约，可重入锁
     *
     * @param key        锁名
     * @param expireTime 锁过期时间
     * @param unit       锁过期时间单位
     * @param awaitTime  锁等待超时时间
     * @param awaitUnit  锁
     * @param retryCount 最大获取次数
     * @param sleepTime  每次获取失败后的睡眠时间
     * @param sleepUnit  睡眠单位
     * @return
     */
    public Boolean tryLockInTime(String key, long expireTime, TimeUnit unit, long awaitTime, TimeUnit awaitUnit, long retryCount, long sleepTime, TimeUnit sleepUnit) {
        //先获取一次
        Boolean isLock = expireTime == -1 ? tryLock(key) : tryLock(key, expireTime, unit);
        //不行再获取
        if (!isLock) {
            long nanos = awaitUnit.toNanos(awaitTime);
            final long deadline = System.nanoTime() + nanos;
            int count = 0;
            while (true) {
                nanos = deadline - System.nanoTime();
                //超时
                if (nanos <= 0L) {
                    return false;
                }
                isLock = expireTime == -1 ? tryLock(key) : tryLock(key, expireTime, unit);
                if (isLock) {
                    return true;
                }
                //如果大于最大获取次数或者线程被中断
                if (count++ > retryCount || Thread.interrupted()) {
                    return false;
                }
                //阻塞
                LockSupport.parkNanos(sleepUnit.toNanos(sleepTime));
            }
        }
        return true;
    }

    /**
     * 按照秒来锁
     *
     * @param key
     * @param time
     * @return
     */
    public Boolean tryLock(String key, long time) {
        return tryLock(key, time, TimeUnit.SECONDS);
    }

    /**
     * 单次锁
     */
    public Boolean tryLock(String key, long expireTime, TimeUnit unit) {
        key = PREFIX + key;
        String threadSign = RedisLockInfoHolder.get();
        if (Objects.isNull(threadSign)) {
            String uuid = UUID.randomUUID().toString();
            String value = uuid + Thread.currentThread().getId();
            threadSign = value;
            RedisLockInfoHolder.setValue(threadSign);
        }
        long millis = unit.toMillis(expireTime);
        //要使用stringRedisTemplate才可以设置上
        Boolean isLock = (Boolean) stringRedisTemplate.execute(getLockScript(), Collections.singletonList(key), threadSign, String.valueOf(millis));
        return isLock;
    }

    public UnLockStatus unLock(String key) {
        key = PREFIX + key;
        String threadSign = RedisLockInfoHolder.get();
        Long result = (Long) stringRedisTemplate.execute(getUnLockScript(), Collections.singletonList(key), threadSign);
        if (Objects.equals(result, UnLockStatus.UNLOCK_SUCCESS.getStatus())) {
            RedisLockInfoHolder.clear();
        }
        return UnLockStatus.getByStatus(result);
    }

    /**
     * 用于未知锁时长
     *
     * @param key
     * @return
     */
    public Boolean tryLock(String key) {
        //先锁一次
        Boolean lock = tryLock(key, INTERVAL_CHECK_EXPIRE, TimeUnit.SECONDS);
        if (lock) {
            DefaultRedisScript extendScript = getExtendScript();
            wheelTimer.newTimeout(new TimerTask() {
                @Override
                public void run(Timeout timeout) throws Exception {
                    //延迟0.8的时间后开始校验，是否存在锁，存在就延期,不存在取消任务执行
                    Boolean isExtend = (Boolean) stringRedisTemplate.execute(extendScript, Collections.singletonList(PREFIX + key), String.valueOf(TimeUnit.MILLISECONDS.convert(INTERVAL_CHECK_EXPIRE, TimeUnit.SECONDS)));
                    if (isExtend) {
                        wheelTimer.newTimeout(this, (long) (INTERVAL_CHECK_EXPIRE * 0.8), TimeUnit.SECONDS);
                    } else {
                        timeout.cancel();
                    }
                }
            }, (long) (INTERVAL_CHECK_EXPIRE * 0.8), TimeUnit.SECONDS);
            return true;
        }
        return false;
    }

    private static class RedisLockInfoHolder {
        static ThreadLocal<String> holder = new ThreadLocal<>();

        private static void clear() {
            holder.remove();
        }

        private static String get() {
            return holder.get();
        }

        private static void setValue(String value) {
            holder.set(value);
        }
    }
}
