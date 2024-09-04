package com.starfish.incubator.lock;

import com.starfish.core.SpringPlus;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.RedisScript;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * RedisLock
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2024-09-04
 */
public class RedisLock implements Lock, Closeable {

    public static final String UNLOCK_SCRIPT = """
            if redis.call("get",KEYS[1]) == ARGV[1]
            then
                return redis.call("del",KEYS[1])
            else
                return 0
            end
            """;

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 锁名称
     */
    private final String name;

    private final String redisKey;

    private static final String REDIS_VALUE_PREFIX = UUID.randomUUID() + "-";

    /**
     * 过期时间，单位毫秒
     */
    private int expire;

    public RedisLock(String name, int expire) {
        stringRedisTemplate = SpringPlus.getBean(StringRedisTemplate.class);
        this.name = name;
        this.redisKey = "starfish:redisLock:" + name;
        this.expire = expire;
    }

    @Override
    public boolean lock() {
        String threadId = String.valueOf(Thread.currentThread().getId());
        String redisValue = REDIS_VALUE_PREFIX + threadId;
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        return Boolean.TRUE.equals(valueOperations.setIfAbsent(redisKey, redisValue, expire, TimeUnit.MILLISECONDS));
    }

    @Override
    public boolean unlock() {
        RedisScript<Boolean> redisScript = RedisScript.of(UNLOCK_SCRIPT, Boolean.class);
        List<String> keys = Collections.singletonList(redisKey);
        String threadId = String.valueOf(Thread.currentThread().getId());
        String args = REDIS_VALUE_PREFIX + threadId;
        return Boolean.TRUE.equals(stringRedisTemplate.execute(redisScript, keys, args));
    }

    @Override
    public void close() throws IOException {
        this.unlock();
    }

}
