package com.zjj.autoconfigure.component.l2cache;

import org.springframework.cache.CacheManager;


/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月11日 20:47
 */
public interface L2CacheManage extends CacheManager {

    public <K, V> L2Cache<K, V> getL2Cache(String cacheName);

    Object getServerId();

    void clearLocal(String cacheName, Object key, CacheOperation cacheOperation);


}
