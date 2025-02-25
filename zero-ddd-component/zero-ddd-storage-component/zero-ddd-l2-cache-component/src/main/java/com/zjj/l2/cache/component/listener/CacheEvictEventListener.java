package com.zjj.l2.cache.component.listener;

import com.zjj.autoconfigure.component.l2cache.EvictEvent;
import com.zjj.autoconfigure.component.l2cache.L2CacheManage;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月12日 20:55
 */
@Slf4j
public record CacheEvictEventListener(L2CacheManage l2CacheManage) implements Consumer<EvictEvent> {

    @Override
    public void accept(EvictEvent evictEvent) {
        if (Objects.equals(evictEvent.getServerId(), l2CacheManage.getServerId())) return;

        log.debug(
                "receive a redis topic message, clear local cache, the cacheName is {}, operation is {}, the key is {}",
                evictEvent.getCacheName(), evictEvent.getCacheOperation(), evictEvent.getKey());
        l2CacheManage.clearLocal(evictEvent.getCacheName(), evictEvent.getKey(),
                evictEvent.getCacheOperation());
    }
}
