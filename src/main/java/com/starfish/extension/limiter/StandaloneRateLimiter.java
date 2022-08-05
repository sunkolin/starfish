package com.starfish.extension.limiter;

/**
 * 单机限流器
 * 使用Guava RateLimiter
 * Guava的RateLimiter提供了令牌桶算法实现
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-07-07
 */
public class StandaloneRateLimiter implements RateLimiter {

    private com.google.common.util.concurrent.RateLimiter guavaRateLimiter;

    public StandaloneRateLimiter(long permitsPerSecond) {
        guavaRateLimiter = com.google.common.util.concurrent.RateLimiter.create(permitsPerSecond);
    }

    @Override
    public boolean acquire(int count) {
        return guavaRateLimiter.tryAcquire(count);
    }

}
