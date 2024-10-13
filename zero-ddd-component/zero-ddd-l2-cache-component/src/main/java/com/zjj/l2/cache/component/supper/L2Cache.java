package com.zjj.l2.cache.component.supper;


import com.github.benmanes.caffeine.cache.Cache;

import java.time.Duration;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月11日 20:10
 */
public interface L2Cache<K, V> extends Cache<K, V> {

    public void setL1Value(K key, V value);

    public void setL2Value(Object key, Object value, Duration expire);

    V getL2Value(String key);

    V getL1Value(K key);

    void clearLocalBatch(Iterable<Object> key);

    void clearLocal(Object key);
}
