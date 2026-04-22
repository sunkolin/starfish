package com.starfish.common.cache.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * RedisCacheImpl
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2026-04-22
 */
@Component
public class RedisCacheImpl implements RedisCache {

    @Autowired
    public RedisTemplate<Object, Object> redisTemplate;

    @Override
    public boolean exist(Object key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public <T> T get(Object key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    @Override
    public void set(Object key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(Object key, Object value, long time, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    @Override
    public void delete(Object key) {
        redisTemplate.delete(key);
    }

    /**
     * 只清空当前选择的数据库（默认是 db0）
     */
    @Override
    public void clear() {
        if (redisTemplate.getConnectionFactory() != null) {
            redisTemplate.getConnectionFactory().getConnection().serverCommands().flushDb();
        }
    }

}
