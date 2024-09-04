package com.starfish.incubator.limiter;

import cn.hutool.core.date.DateUtil;

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
public class CounterRateLimiter implements RateLimiter {

    private final long permits;

    private static final AtomicLong COUNTER = new AtomicLong(0);

    private static Date TIMESTAMP = DateUtil.dateSecond();

    /**
     * 时间间隔
     */
    private static final long TIME_INTERVAL = 1000L;

    /**
     * 构造方法
     *
     * @param name             限流器名称，不同业务使用不同的名称
     * @param permits 每秒限流数量
     */
    public CounterRateLimiter(String name, long permits) {
        this.permits = permits;
    }

    @Override
    public boolean acquire(int count) {
        synchronized (this) {
            Date now = DateUtil.dateSecond();
            if (now.getTime() - TIMESTAMP.getTime() < TIME_INTERVAL) {
                if (COUNTER.get() < permits) {
                    COUNTER.incrementAndGet();
                    return true;
                } else {
                    return false;
                }
            } else {
                COUNTER.set(0);
                TIMESTAMP = now;
                return false;
            }
        }
    }

}
