package com.starfish.trial.limiter;

import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * RedisRateLimiterService
 *
 * @author neacle
 * @version 1.0.0
 * @since 2019-07-11
 */
@Slf4j
@Service
public class RedisRateLimiter implements RateLimiter{

    @Resource
    private RedisTemplate redisTemplate;

    private DefaultRedisScript<Long> defaultRedisScript;

    private final RedisSerializer<String> stringRedisSerializer = new StringRedisSerializer();

    @PostConstruct
    public void init() {
        defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("redis-rate-limiter.lua")));
        defaultRedisScript.setResultType(Long.class);
    }

    public void acquire(String key, int qps) {
        while (true) {
            ImmutableList<String> keys = ImmutableList.of(key);
            long timestamp = System.currentTimeMillis();
            String[] params = {String.valueOf(qps), String.valueOf(timestamp)};
            long token = (Long) redisTemplate.execute(defaultRedisScript, stringRedisSerializer, stringRedisSerializer, keys, params);
            if (token == 1) {
                log.info("RedisRateLimiterService acquire,key={},qps={},timestamp={}", key, qps, timestamp);
                break;
            }
            try {
                TimeUnit.MICROSECONDS.sleep(10);
            } catch (InterruptedException e) {
                log.error("RedisRateLimiterService acquire error,key={},qps={},timestamp={}", key, qps, timestamp, e);
            }
        }
    }

    @Override
    public boolean tryAcquire() {
        // TODO
        return false;
    }

}
