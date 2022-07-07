package com.starfish.extension.limiter;

import org.springframework.stereotype.Service;

/**
 * 集群限流器
 * 滑动窗口算法实现
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2019-07-11
 */
@Service
public class ClusterRateLimiter extends RollingWindowRateLimiter {

    /**
     * ClusterRateLimiter
     *
     * @param redisKey 缓存Key
     * @param permits  一秒允许的数量
     */
    public ClusterRateLimiter(String redisKey, long permits) {
        super(redisKey, permits);
    }

}
