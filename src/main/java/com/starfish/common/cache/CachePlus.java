package com.starfish.common.cache;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

/**
 * CachePlus
 * 使用caffeine做本地缓存
 * TODO 解决未配置caffeine启动失败问题
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-06-11
 */
//@ConditionalOnBean("caffeine")
public class CachePlus implements Cache {

    private final com.github.benmanes.caffeine.cache.Cache<Object, Object> caffeineCache;

    public CachePlus(com.github.benmanes.caffeine.cache.Cache<Object, Object> caffeineCache) {
        this.caffeineCache = caffeineCache;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Object key) {
        return (T) caffeineCache.getIfPresent(key);
    }

    @Override
    public boolean exist(Object key) {
        Boolean exist = get(key + ":exist");
        return (exist == null || !exist) ? Boolean.FALSE : Boolean.TRUE;
    }

    @Override
    public void set(Object key, Object value) {
        caffeineCache.put(key, value);
        caffeineCache.put(key + ":exist", true);
    }

    @Override
    public void remove(Object key) {
        caffeineCache.invalidate(key);
        caffeineCache.invalidate(key + ":exist");
    }

    @Override
    public void clear() {
        caffeineCache.cleanUp();
    }

}
