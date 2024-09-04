package com.starfish.incubator.limiter;

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

    private final com.google.common.util.concurrent.RateLimiter guavaRateLimiter;

    /**
     * 构造方法
     *
     * @param name             限流器名称，不同业务使用不同的名称
     * @param permits 每秒限流数量
     */
    public StandaloneRateLimiter(String name, long permits) {
        guavaRateLimiter = com.google.common.util.concurrent.RateLimiter.create(permits);
    }

    @Override
    public boolean acquire(int count) {
        return guavaRateLimiter.tryAcquire(count);
    }

}
