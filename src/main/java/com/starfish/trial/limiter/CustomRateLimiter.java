package com.starfish.trial.limiter;

/**
 * RateLimiter
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2019-07-11
 */
public interface CustomRateLimiter {

    /**
     * acquire
     *
     * @return result
     */
    default boolean acquire() {
        return acquire(1);
    }

    /**
     * acquire
     *
     * @param count 数量
     * @return result
     */
    boolean acquire(int count);

}
