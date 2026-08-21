package com.starfish.core.model;

/**
 * Pair
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2026-08-06
 */
public class Pair<K, V> extends KeyValue<K, V> {

    public static <K, V> Pair<K, V> of(K key, V value) {
        return (Pair<K, V>) KeyValue.of(key, value);
    }

}
