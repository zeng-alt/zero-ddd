package com.zjj.cache.component.repository.impl;

import com.zjj.autoconfigure.component.json.JsonHelper;
import com.zjj.cache.component.repository.RedisStringRepository;
import com.zjj.core.component.exception.UtilException;
import com.zjj.json.component.utils.JacksonHelper;
import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Modifier;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年06月18日 18:56
 */
@Component
public class RedisStringRepositoryImpl extends RedisStringRepository {


	private final JsonHelper jsonHelper;

	@Autowired
	public RedisStringRepositoryImpl(RedissonClient template, JsonHelper jsonHelper) {
		super(template);
		this.jsonHelper = jsonHelper;
	}

	@Override
	public Object get(String key) {
		return template.getBucket(key).get();
	}

	@Override
	public <T> T get(String key, Class<T> tClass) {
		// 判断tclass是不是final的
		if (Modifier.isFinal(tClass.getModifiers())) {
			RBucket<String> bucket = template.getBucket(key);
			return jsonHelper.parseObject(bucket.get(), tClass);
		}
		RBucket<T> bucket = template.getBucket(key);
		return bucket.get();
	}


	@Override
	@NonNull
	public List<Object> getAll(@NonNull String key) {
		return getAll(key, Object.class);
	}

	@Override
	public <T> List<T> getAll(String key, Class<T> tClass) {
		if (!StringUtils.hasLength(key)) {
			throw new UtilException("key is not null");
		}
		RKeys rKeys = template.getKeys();
		Iterator<String> iterator = rKeys.getKeysByPattern(key + "*").iterator();
		List<T> result = new ArrayList<>();
		RBucket<T> bucket = null;
		while (iterator.hasNext()) {
			bucket = template.getBucket(key);
			result.add(bucket.get());
		}
		return result;
	}

	@Override
	public void put(String key, Object value) {
		template.getBucket(key).set(value);
	}

	@Override
	public void putIfAbsent(String key, Object value) {
		RBucket<Object> bucket = template.getBucket(key);
		bucket.setIfAbsent(value);
	}

	@Override
	public void put(String key, Object value, long expireTime) {
		template.getBucket(key).set(value, expireTime, TimeUnit.SECONDS);
	}

	@Override
	public boolean putIfAbsent(String key, Object value, long expireTime) {
		RBucket<Object> bucket = template.getBucket(key);
		return bucket.setIfAbsent(value, Duration.ofSeconds(expireTime));
	}

	@Override
	public long increment(String key) {
		return template.getAtomicLong(key).incrementAndGet();
	}

	@Override
	public long decrement(String key) {
		return template.getAtomicLong(key).decrementAndGet();
	}

	/**
	 * 尝试获取分布式锁。
	 * @param lockName 锁的名称
	 * @return 是否成功获取锁
	 */
	@Override
	public boolean tryLock(String lockName) {
		RLock lock = template.getLock(lockName);
		return lock.tryLock();
	}

	/**
	 * 获取分布式锁。
	 * @param lockName 锁的名称
	 * @param waitTime 等待时间（毫秒）
	 * @param leaseTime 租约时间（毫秒）
	 * @return 是否成功获取锁
	 */
	@Override
	public boolean tryLock(String lockName, long waitTime, long leaseTime) throws InterruptedException {
		RLock lock = template.getLock(lockName);
		return lock.tryLock(waitTime, leaseTime, TimeUnit.MILLISECONDS);
	}

	/**
	 * 释放分布式锁。
	 * @param lockName 锁的名称
	 */
	@Override
	public void unlock(String lockName) {
		RLock lock = template.getLock(lockName);
		lock.unlock();
	}

}
