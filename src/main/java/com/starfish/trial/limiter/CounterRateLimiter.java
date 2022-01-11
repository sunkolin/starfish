package com.starfish.trial.limiter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 计数器算法，网上有说是令牌桶算法，需要具体测试 TODO
 * 简单介绍4种限流算法：计数器算法、滑动窗口计数器算法、漏桶算法、令牌桶算法
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-01-19
 */
@Slf4j
@Service
public class CounterRateLimiter implements RateLimiter {

    private static com.google.common.util.concurrent.RateLimiter GUAVA_RATE_LIMITER;

    CounterRateLimiter(long permitsPerSecond) {
        GUAVA_RATE_LIMITER = com.google.common.util.concurrent.RateLimiter.create(permitsPerSecond);
    }

    @Override
    public boolean tryAcquire() {
        return GUAVA_RATE_LIMITER.tryAcquire();
    }

}
