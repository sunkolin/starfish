package com.starfish.extension.limiter;

import com.google.common.util.concurrent.RateLimiter;

/**
 * 令牌桶算法限流器
 * 单机限流器
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-03-02
 */
public class TokenBucketRateLimiter implements CustomRateLimiter {

    private static RateLimiter GUAVA_RATE_LIMITER;

    public TokenBucketRateLimiter(long permitsPerSecond) {
        GUAVA_RATE_LIMITER = RateLimiter.create(permitsPerSecond);
    }

    @Override
    public boolean acquire(int count) {
        return GUAVA_RATE_LIMITER.tryAcquire(count);
    }

}
