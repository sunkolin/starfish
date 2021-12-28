package com.starfish.trial.limiter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * 滑动窗口算法
 * 简单介绍4种限流算法：计数器算法、滑动窗口计数器算法、漏桶算法、令牌桶算法
 *
 * @author neacle
 * @version 1.0.0
 * @since 2021-01-19
 */
@Slf4j
@Service
public class SlidingWindowRateLimiter implements RateLimiter {

    /**
     * 一秒允许的数量
     */
    public long permits;

    /**
     * 限流间隔时间，单位毫秒，默认10毫秒
     */
    public long intervalMillisecond = 10;

    public String name;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    SlidingWindowRateLimiter(String name, long permits) {
        this.name = name;
        this.permits = permits;
    }

    @Override
    public boolean tryAcquire() {
        long score = System.currentTimeMillis();

        // 如果不存在此key，返回ture
        if (exist() && greaterThanOrEqualToPermits(score)) {
            return false;
        } else {
            ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
            String value = UUID.randomUUID().toString();
            zSetOperations.add(name, value, score);
            return true;
        }
    }

    public boolean exist() {
        return redisTemplate.hasKey(name);
    }

    /**
     * 判断当前数量是否大于等于允许的数量
     *
     * @param max maxScore
     * @return 结果
     */
    public boolean greaterThanOrEqualToPermits(long max) {
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        double min = max - intervalMillisecond;
        Long count = zSetOperations.count(name, min, max);
        long intervalPermits = (long) (permits / (1000D / intervalMillisecond));
        return (count != null && count > intervalPermits);
    }

}
