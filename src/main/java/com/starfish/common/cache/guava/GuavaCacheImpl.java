package com.starfish.common.cache.guava;

import com.starfish.common.cache.Cache;

/**
 * Cache,使用caffeine做本地缓存
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-06-11
 */
public class GuavaCacheImpl implements GuavaCache {

    /**
     * exist后缀
     */
    public static final String EXIST_SUFFIX = ":exist";

    private final com.github.benmanes.caffeine.cache.Cache<Object, Object> caffeineCache;

    public GuavaCacheImpl(com.github.benmanes.caffeine.cache.Cache<Object, Object> caffeineCache) {
        this.caffeineCache = caffeineCache;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Object key) {
        return (T) caffeineCache.getIfPresent(key);
    }

    @Override
    public boolean exist(Object key) {
        Boolean exist = get(key + EXIST_SUFFIX);
        return (exist == null || !exist) ? Boolean.FALSE : Boolean.TRUE;
    }

    @Override
    public void set(Object key, Object value) {
        caffeineCache.put(key, value);
        caffeineCache.put(key + EXIST_SUFFIX, true);
    }

    @Override
    public void delete(Object key) {
        caffeineCache.invalidate(key);
        caffeineCache.invalidate(key + EXIST_SUFFIX);
    }

    @Override
    public void clear() {
        caffeineCache.cleanUp();
    }

}
