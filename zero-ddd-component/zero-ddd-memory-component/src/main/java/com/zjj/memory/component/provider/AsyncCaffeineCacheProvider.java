package com.zjj.memory.component.provider;

import com.github.benmanes.caffeine.cache.AsyncCache;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月08日 20:12
 */
@FunctionalInterface
public interface AsyncCaffeineCacheProvider {

	Tuple<AsyncCache<Object, Object>> provider();

}
