package com.starfish.core.model;

/**
 * KeyValue
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2026-08-06
 */
public class KeyValue<K, V> {

    public KeyValue() {

    }

    private KeyValue(K key, V value) {
        this.key = key;
        this.value = value;
    }

    private K key;

    private V value;

    public static <K, V> KeyValue<K, V> of(K key, V value) {
        return new KeyValue<>(key, value);
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

}
