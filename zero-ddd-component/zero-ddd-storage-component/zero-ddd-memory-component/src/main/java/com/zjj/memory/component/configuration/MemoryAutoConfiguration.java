package com.zjj.memory.component.configuration;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.zjj.memory.component.provider.AsyncCaffeineCacheProvider;
import com.zjj.memory.component.provider.CaffeineCacheProvider;
import com.zjj.memory.component.provider.Tuple;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月08日 20:16
 */
@AutoConfiguration
public class MemoryAutoConfiguration {

	@Bean
	public CacheManagerCustomizer<CaffeineCacheManager> memoryAutoConfiguration(
			ObjectProvider<CaffeineCacheProvider> caffeineCacheProviders,
			ObjectProvider<AsyncCaffeineCacheProvider> asyncCaffeineCacheProviders) {
		return cacheManager -> {
			cacheManager.setAllowNullValues(false);
			caffeineCacheProviders.orderedStream().forEach(provider -> {
				Tuple<Cache<Object, Object>> tuple = provider.provider();
				cacheManager.registerCustomCache(tuple._1(), tuple._2());
			});

			asyncCaffeineCacheProviders.orderedStream().forEach(provider -> {
				Tuple<AsyncCache<Object, Object>> tuple = provider.provider();
				cacheManager.registerCustomCache(tuple._1(), tuple._2());
			});
		};
	}

}
