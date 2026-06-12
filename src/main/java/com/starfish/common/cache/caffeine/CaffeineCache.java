package com.starfish.common.cache.caffeine;

import com.starfish.common.cache.Cache;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * GuavaCache
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2026-04-22
 */
public interface CaffeineCache extends Cache {

    @Override
    default void set(String key, Object value, Duration duration) {
        throw new UnsupportedOperationException();
    }

}
