package com.starfish.incubator.limiter;

import com.starfish.core.SpringPlus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.UUID;

/**
 * 滑动窗口算法
 * 简单介绍4种限流算法：计数器算法、滑动窗口计数器算法、漏桶算法、令牌桶算法
 * 参考：<a href="https://www.cnblogs.com/linjiqin/p/9707713.html">三种常见的限流算法</a>
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-01-19
 */
@Slf4j
public class RollingWindowRateLimiter implements RateLimiter {

    /**
     * 一秒允许的数量
     */
    private final long permits;

    /**
     * 限流间隔时间，单位毫秒，默认10毫秒
     */
    public static final long INTERVAL_MILLISECOND = 10;

    public final String redisKey;

    private final StringRedisTemplate stringRedisTemplate;

    public RollingWindowRateLimiter(String name, long permits) {
        stringRedisTemplate = SpringPlus.getBean(StringRedisTemplate.class);
        this.redisKey = "starfish:RateLimiter:" + name;
        this.permits = permits;
    }

    @Override
    public boolean acquire(int count) {
        long score = System.currentTimeMillis();

        // 如果不存在此key，返回ture
        if (exist() && greaterThanOrEqualToPermits(score)) {
            return false;
        } else {
            ZSetOperations<String, String> zSetOperations = stringRedisTemplate.opsForZSet();
            String value = UUID.randomUUID().toString();
            zSetOperations.add(redisKey, value, score);
            return true;
        }
    }

    public boolean exist() {
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(redisKey));
    }

    /**
     * 判断当前数量是否大于等于允许的数量
     *
     * @param max maxScore
     * @return 结果
     */
    public boolean greaterThanOrEqualToPermits(long max) {
        ZSetOperations<String, String> zSetOperations = stringRedisTemplate.opsForZSet();
        long min = max - INTERVAL_MILLISECOND;
        Long count = zSetOperations.count(redisKey, min, max);
        long intervalPermits = (long) (permits / (1000D / INTERVAL_MILLISECOND));
        return (count != null && count > intervalPermits);
    }

}
