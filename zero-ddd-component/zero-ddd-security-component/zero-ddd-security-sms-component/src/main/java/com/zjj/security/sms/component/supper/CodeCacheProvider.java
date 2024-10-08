package com.zjj.security.sms.component.supper;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.zjj.memory.component.provider.CaffeineCacheProvider;
import com.zjj.memory.component.provider.Tuple;

import java.util.concurrent.TimeUnit;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月08日 21:28
 */
public class CodeCacheProvider implements CaffeineCacheProvider {

	public Cache<Object, Object> codeCache() {
		return Caffeine.newBuilder().initialCapacity(100).maximumSize(500).expireAfterAccess(1, TimeUnit.MINUTES)
				.weakKeys().recordStats().build();
	}

	@Override
	public Tuple<Cache<Object, Object>> provider() {
		return Tuple.of("code", codeCache());
	}

}
