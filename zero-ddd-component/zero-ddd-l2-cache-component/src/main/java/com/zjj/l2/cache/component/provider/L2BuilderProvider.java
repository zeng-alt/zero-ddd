package com.zjj.l2.cache.component.provider;

import com.zjj.l2.cache.component.supper.RedissonCaffeineCache;
import com.zjj.memory.component.provider.Tuple;
import reactor.util.function.Tuple2;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月25日 21:20
 */
public interface L2BuilderProvider {
    Tuple<RedissonCaffeineCache.Builder> consumerBuilder();
}
