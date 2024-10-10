package com.zjj.cache.component.repository.impl;

import com.zjj.cache.component.repository.RedisListRepository;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年06月18日 18:56
 */
@Component
public class RedisListRepositoryImpl<V> extends RedisListRepository<V> {

	@Autowired
	public RedisListRepositoryImpl(RedissonClient template) {
		super(template);
	}

	@Override
	public void addToList(String key, V value) {
		put(key, value);
	}

	@Override
	public V get(String key) {
		// return template.opsForList().index(key, 0);
		return null;
	}

	@Override
	public void put(String key, V value) {
		// template.opsForList().rightPush(key, value);
	}

	@Override
	public void putIfAbsent(String key, V value) {
		// template.opsForList().rightPushIfPresent(key, value);
	}

}
