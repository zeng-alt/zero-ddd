/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zjj.memory.component.configuration;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.CaffeineSpec;
import com.zjj.autoconfigure.component.security.rbac.RbacCacheManage;
import com.zjj.memory.component.supper.CaffeineRbacCacheManage;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizers;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Caffeine cache configuration.
 *
 * @author Eddú Meléndez
 */
@AutoConfiguration
@EnableConfigurationProperties(CacheProperties.class)
@ConditionalOnClass({ Caffeine.class, CaffeineCacheManager.class })
class CaffeineCacheConfiguration {

	private static final String SPEC = "initialCapacity=100,maximumSize=500,expireAfterAccess=5m,recordStats";

	@Bean
	@Primary
	@ConditionalOnMissingBean(CaffeineCacheManager.class)
	public CaffeineCacheManager caffeineCacheManager(CacheProperties cacheProperties, ObjectProvider<CacheManagerCustomizer<CaffeineCacheManager>> customizers,
                                      ObjectProvider<Caffeine<Object, Object>> caffeine, ObjectProvider<CaffeineSpec> caffeineSpec,
                                      ObjectProvider<CacheLoader<Object, Object>> cacheLoader) {
		CaffeineCacheManager cacheManager = createCacheManager(cacheProperties, caffeine, caffeineSpec, cacheLoader);
		if (CacheType.CAFFEINE.equals(cacheProperties.getType())) {
			List<String> cacheNames = cacheProperties.getCacheNames();
			if (!CollectionUtils.isEmpty(cacheNames)) {
				cacheManager.setCacheNames(cacheNames);
			}
		}
		customizers.forEach(customizer -> customizer.customize(cacheManager));
		return cacheManager;
	}

	@Bean
	@ConditionalOnMissingClass("com.zjj.rbac.component.supper.RbacCacheManage")
	public CacheManagerCustomizer<CaffeineCacheManager> rbacCacheMangerCustomizer() {
		return cacheManager -> {
            cacheManager.registerCustomCache(RbacCacheManage.HTTP_RESOURCE_KEY, Caffeine.newBuilder().build());
            cacheManager.registerCustomCache(RbacCacheManage.GRAPHQL_RESOURCE_KEY, Caffeine.newBuilder().build());
            cacheManager.registerCustomCache(RbacCacheManage.ROLE_KEY, Caffeine.newBuilder().build());
        };
	}

	@Bean
	@ConditionalOnMissingBean(RbacCacheManage.class)
	@ConditionalOnMissingClass("com.zjj.rbac.component.supper.RbacCacheManage")
	public CaffeineRbacCacheManage caffeineRbacCacheManage(CaffeineCacheManager cacheManager) {
		return new CaffeineRbacCacheManage(cacheManager);
	}

	private CaffeineCacheManager createCacheManager(CacheProperties cacheProperties,
			ObjectProvider<Caffeine<Object, Object>> caffeine, ObjectProvider<CaffeineSpec> caffeineSpec,
			ObjectProvider<CacheLoader<Object, Object>> cacheLoader) {
		CaffeineCacheManager cacheManager = new CaffeineCacheManager();
		setCacheBuilder(cacheProperties, caffeineSpec.getIfAvailable(), caffeine.getIfAvailable(), cacheManager);
		cacheLoader.ifAvailable(cacheManager::setCacheLoader);
		return cacheManager;
	}

	private void setCacheBuilder(CacheProperties cacheProperties, CaffeineSpec caffeineSpec,
			Caffeine<Object, Object> caffeine, CaffeineCacheManager cacheManager) {
		String specification = cacheProperties.getCaffeine().getSpec();
		cacheManager.setCacheSpecification(SPEC);

		if (StringUtils.hasText(specification)) {
			cacheManager.setCacheSpecification(specification);
		}
		else if (caffeineSpec != null) {
			cacheManager.setCaffeineSpec(caffeineSpec);
		}
		else if (caffeine != null) {
			cacheManager.setCaffeine(caffeine);
		}
	}

}
