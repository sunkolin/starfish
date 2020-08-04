package com.sunny.util;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

/**
 * EhcacheUtil
 *
 * @author sunny
 * @version 1.0.0
 * @since 2018-06-06
 */
@SuppressWarnings("unused")
public class EhcacheUtil {

    private static CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
            .withCache("customCache", CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class,
                    ResourcePoolsBuilder.heap(100)).build()).build(true);

    private static final Object LOCKER = new Object();

    /**
     * 获取缓存
     *
     * @param cacheName 缓存名称
     * @param keyType   key类型
     * @param valueType value类型
     * @param <K>       K
     * @param <V>       V
     */
    private static <K, V> Cache<K, V> getCache(String cacheName, Class<K> keyType, Class<V> valueType) {
        Cache<K, V> cache = cacheManager.getCache(cacheName, keyType, valueType);
        if (cache == null) {
            synchronized (LOCKER) {
                cache = cacheManager.getCache(cacheName, keyType, valueType);
                if (cache == null) {
                    CacheConfiguration<K, V> cacheConfiguration = CacheConfigurationBuilder.newCacheConfigurationBuilder(keyType, valueType,
                            ResourcePoolsBuilder.heap(100)).build();
                    cacheManager.createCache(cacheName, cacheConfiguration);
                    cache = cacheManager.getCache(cacheName, keyType, valueType);
                }
            }
        }
        return cache;
    }

    /**
     * 向缓存中存入指定的值
     *
     * @param cacheName 缓存名称
     * @param keyType   key类型
     * @param valueType value类型
     * @param key       键
     * @param value     值
     * @param <K>       K
     * @param <V>       V
     */
    public static <K, V> void put(String cacheName, Class<K> keyType, Class<V> valueType, K key, V value) {
        getCache(cacheName, keyType, valueType).put(key, value);
    }

    /**
     * 删除缓存下指定key的数据
     *
     * @param cacheName 缓存名称
     * @param keyType   key类型
     * @param valueType value类型
     * @param key       键
     * @param <K>       K
     * @param <V>       V
     */
    public static <K, V> void remove(String cacheName, Class<K> keyType, Class<V> valueType, K key) {
        getCache(cacheName, keyType, valueType).remove(key);
    }

    /**
     * 删除cache下全部缓存
     *
     * @param cacheName 缓存名称
     * @param keyType   key类型
     * @param valueType value类型
     * @param <K>       K
     * @param <V>       V
     */
    public static <K, V> void clear(String cacheName, Class<K> keyType, Class<V> valueType) {
        getCache(cacheName, keyType, valueType).clear();
    }

    /**
     * 获取缓存下指定键的数据
     *
     * @param cacheName 缓存名称
     * @param keyType   key类型
     * @param valueType value类型
     * @param key       键
     * @param <K>       K
     * @param <V>       V
     * @return 值
     */
    public static <K, V> V get(String cacheName, Class<K> keyType, Class<V> valueType, K key) {
        return getCache(cacheName, keyType, valueType).get(key);
    }

}


