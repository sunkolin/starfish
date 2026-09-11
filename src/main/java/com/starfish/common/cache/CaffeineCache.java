package com.starfish.common.cache;

import java.time.Duration;

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
