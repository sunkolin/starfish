package com.starfish.extension.limiter;

import cn.hutool.core.date.DateUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 单机限流器
 * 计数器算法
 * 简单介绍4种限流算法：计数器算法、滑动窗口计数器算法、漏桶算法、令牌桶算法
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-01-19
 */
@Service
public class CounterRateLimiter implements RateLimiter {

    private final long permitsPerSecond;

    private final AtomicLong counter = new AtomicLong(0);

    private static Date timestamp = DateUtil.dateSecond();

    /**
     * 时间间隔
     */
    private static final long TIME_INTERVAL = 1000L;

    public CounterRateLimiter(long permitsPerSecond) {
        this.permitsPerSecond = permitsPerSecond;
    }

    @Override
    public boolean acquire(int count) {
        synchronized (this) {
            Date now = DateUtil.dateSecond();
            if (now.getTime() - timestamp.getTime() < TIME_INTERVAL) {
                if (counter.get() < permitsPerSecond) {
                    counter.incrementAndGet();
                    return true;
                } else {
                    return false;
                }
            } else {
                counter.set(0);
                timestamp = now;
                return false;
            }
        }
    }

}
