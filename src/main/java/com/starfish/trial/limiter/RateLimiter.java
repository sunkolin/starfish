package com.starfish.trial.limiter;

/**
 * RateLimiter
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2019-07-11
 */
public interface RateLimiter {

    /**
     * tryAcquire
     *
     * @return result
     */
    boolean tryAcquire();

}
