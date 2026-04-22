package com.starfish.common.cache.caffeine;

import com.starfish.common.cache.Cache;

import java.util.concurrent.TimeUnit;

/**
 * GuavaCache
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2026-04-22
 */
public interface CaffeineCache extends Cache {

    default void set(String key, Object value, long time, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

}
