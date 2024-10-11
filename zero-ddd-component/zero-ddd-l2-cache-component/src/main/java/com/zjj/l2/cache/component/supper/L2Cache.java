package com.zjj.l2.cache.component.supper;


import com.github.benmanes.caffeine.cache.Cache;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月11日 20:10
 */
public interface L2Cache<K, V> extends Cache<K, V> {

    public void setL1Value(K key, V value);

    V getL2Value(String key);

    V getL1Value(K key);
}
