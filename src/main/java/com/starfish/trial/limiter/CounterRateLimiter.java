package com.starfish.trial.limiter;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 计数器，单机限流
 * 简单介绍4种限流算法：计数器算法、滑动窗口计数器算法、漏桶算法、令牌桶算法
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-01-19
 */
@Slf4j
@Service
public class CounterRateLimiter implements CustomRateLimiter {

    private final long permitsPerSecond;

    private final AtomicLong counter = new AtomicLong(0);

    private static Date timestamp = DateUtil.dateSecond();

    public CounterRateLimiter(long permitsPerSecond) {
        this.permitsPerSecond = permitsPerSecond;
    }

    @Override
    public boolean acquire(int count) {
        synchronized (this) {
            Date now = DateUtil.dateSecond();
            if (now.getTime() - timestamp.getTime() < 1000) {
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
