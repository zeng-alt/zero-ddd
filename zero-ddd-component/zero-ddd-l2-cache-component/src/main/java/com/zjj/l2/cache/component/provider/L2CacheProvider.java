package com.zjj.l2.cache.component.provider;

import com.github.benmanes.caffeine.cache.Cache;
import com.zjj.memory.component.provider.Tuple;
import reactor.util.function.Tuple2;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月25日 21:10
 */
public interface L2CacheProvider {

    Tuple<Cache<Object, Object>> consumerCache();
}
