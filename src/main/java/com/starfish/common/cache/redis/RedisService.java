package com.starfish.common.cache.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * RedisService
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2026-04-22
 */
public class RedisService implements RedisCache {

    private final RedisTemplate<Object, Object> redisTemplate;

    public RedisService(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 指定缓存过期时间
     */
    public void expire(String key, long seconds) {
        if (seconds > 0) {
            redisTemplate.expire(key, Duration.ofSeconds(seconds));
        }
    }

    /**
     * 获取 key 过期时间
     * 返回：-1=永久有效，-2=key不存在
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断 key 是否存在
     */
    public Boolean exist(String key) {
        return redisTemplate.hasKey(key);
    }


    /**
     * 获取缓存
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return key == null ? null : (T) redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置缓存
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

//    @Override
//    public void set(String key, Object value, long time, TimeUnit timeUnit) {
//        if (time > 0) {
//            redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(time));
//        }
//    }

    @Override
    public void set(String key, Object value, Duration duration) {
        if (duration != null) {
            redisTemplate.opsForValue().set(key, value, duration);
        }
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 设置缓存并指定过期时间（秒）
     */
    public void set(String key, Object value, long seconds) {
        if (seconds > 0) {
            redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(seconds));
        }
    }

    /**
     * 删除缓存
     */
    public void delete(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    public void clear() {
        if (redisTemplate.getConnectionFactory() != null) {
            redisTemplate.getConnectionFactory().getConnection().serverCommands().flushDb();
        }
    }

    /**
     * 递增
     */
    public long increment(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     */
    public long decrement(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

}