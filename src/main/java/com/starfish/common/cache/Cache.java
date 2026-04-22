package com.starfish.common.cache;

import java.util.concurrent.TimeUnit;

/**
 * Cache
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-06-11
 */
public interface Cache {

    /**
     * 判断一个key是否已经缓存
     *
     * @param key 键
     * @return 结果
     */
    boolean exist(Object key);

    /**
     * 获取，如果不存在则返回null
     *
     * @param key 键
     * @param <T> T
     * @return 结果
     */
    <T> T get(Object key);

    /**
     * 设置
     *
     * @param key   键
     * @param value 值
     */
    void set(Object key, Object value);

    /**
     * 设置
     *
     * @param key   键
     * @param value 值
     */
    void set(Object key, Object value, long time, TimeUnit timeUnit);

    /**
     * 移除
     *
     * @param key 键
     */
    void delete(Object key);

    /**
     * 清除
     */
    void clear();

}
