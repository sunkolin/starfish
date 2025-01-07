package com.starfish.incubator.limiter;

/**
 * 集群限流器
 * 滑动窗口算法实现
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2019-07-11
 */
public class ClusterRateLimiter extends RollingWindowRateLimiter {

    /**
     * ClusterRateLimiter
     *
     * @param name    限流器名称，不同业务使用不同的名称
     * @param permits 一秒允许的数量
     */
    public ClusterRateLimiter(String name, long permits) {
        super(name, permits);
    }

}
