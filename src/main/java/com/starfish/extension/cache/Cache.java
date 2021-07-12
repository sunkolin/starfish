package com.starfish.extension.cache;

/**
 * Cache
 *
 * @author sunny
 * @version 1.0.0
 * @since 2021-06-11
 */
public interface Cache {

    /**
     * 获取，如果不存在则返回null
     *
     * @param key 键
     * @param <T> T
     * @return 结果
     */
    @SuppressWarnings("unchecked")
    <T> T get(Object key);

    /**
     * 判断一个key是否已经缓存
     *
     * @param key 键
     * @return 结果
     */
    boolean exist(Object key);

    /**
     * 设置
     *
     * @param key   键
     * @param value 值
     */
    void set(Object key, Object value);

    /**
     * 移除
     *
     * @param key 键
     */
    void remove(Object key);

    /**
     * 清除
     */
    void clear();

}
