package com.starfish.common.cache.caffeine;

/**
 * Cache,使用caffeine做本地缓存
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-06-11
 */
public class CaffeineService implements CaffeineCache {

    private final com.github.benmanes.caffeine.cache.Cache<Object, Object> caffeineCache;

    public CaffeineService(com.github.benmanes.caffeine.cache.Cache<Object, Object> caffeineCache) {
        this.caffeineCache = caffeineCache;
    }

    @Override
    public Boolean exist(String key) {
        return caffeineCache.getIfPresent(key) != null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(String key) {
        return (T) caffeineCache.getIfPresent(key);
    }

    @Override
    public void set(String key, Object value) {
        caffeineCache.put(key, value);
    }

    @Override
    public void delete(String key) {
        caffeineCache.invalidate(key);
    }

    @Override
    public void clear() {
        caffeineCache.cleanUp();
    }

}
