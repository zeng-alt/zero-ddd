package com.zjj.cache.component.repository;

import org.redisson.api.RedissonClient;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年06月18日 18:56
 */
public abstract class RedisStringRepository extends RedisCrudRepository<String, Object> {

	public RedisStringRepository(RedissonClient template) {
		super(template);
	}

	public abstract <T> T get(String key, Class<T> tClass);


	public abstract List<Object> getAll(String key);


	public abstract <T> List<T> getAll(String key, Class<T> tClass);

	public abstract void put(String key, Object value, long expireTime);

	public abstract boolean putIfAbsent(String key, Object value, long expireTime);

	public abstract long increment(String key);

	public abstract long decrement(String key);

	public abstract boolean tryLock(String lockName);

	public abstract boolean tryLock(String lockName, long waitTime, long leaseTime) throws InterruptedException;

	public abstract void unlock(String lockName);

}
