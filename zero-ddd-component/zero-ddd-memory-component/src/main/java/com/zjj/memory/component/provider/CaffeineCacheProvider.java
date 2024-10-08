package com.zjj.memory.component.provider;

import com.github.benmanes.caffeine.cache.Cache;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月08日 20:11
 */
@FunctionalInterface
public interface CaffeineCacheProvider {

	Tuple<Cache<Object, Object>> provider();

}
