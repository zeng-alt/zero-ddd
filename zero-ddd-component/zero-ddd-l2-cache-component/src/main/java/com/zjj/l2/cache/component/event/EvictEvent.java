package com.zjj.l2.cache.component.event;

import com.zjj.l2.cache.component.enums.CacheOperation;

import java.io.Serializable;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月12日 20:53
 */
public record EvictEvent(
        Object serverId,
        String cacheName,
        CacheOperation cacheOperation,
        Object key) implements Serializable {
}
