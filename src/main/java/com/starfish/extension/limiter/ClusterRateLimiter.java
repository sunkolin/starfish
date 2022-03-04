package com.starfish.extension.limiter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 滑动窗口算法限流器
 * 集群限流
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2019-07-11
 */
@Slf4j
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
