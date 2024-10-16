package com.zjj.autoconfigure.component.l2cache;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月12日 20:53
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class EvictEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Object serverId;
    private String cacheName;
    private CacheOperation cacheOperation;
    private Object key;

    public EvictEvent() {
    }

    public EvictEvent(Object serverId, String cacheName, CacheOperation cacheOperation, Object key) {
        this.serverId = serverId;
        this.cacheName = cacheName;
        this.cacheOperation = cacheOperation;
        this.key = key;
    }
}
