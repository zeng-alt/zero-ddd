package com.zjj.cache.component.repository.impl;

import com.zjj.autoconfigure.component.json.JsonHelper;
import com.zjj.autoconfigure.component.redis.RedisStringRepository;
import com.zjj.core.component.exception.UtilException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;
import org.redisson.api.listener.SetObjectListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年06月18日 18:56
 */
@Slf4j
@Component
public class RedisStringRepositoryImpl extends RedisStringRepository {



	@Autowired
	public RedisStringRepositoryImpl(RedissonClient template) {
		super(template);
	}

	@Override
	public <T> T get(String key) {
		RBucket<T> bucket = template.getBucket(key);
		return bucket.get();
	}


	@Override
	@NonNull
	public List<Object> getAll(@NonNull String key) throws ExecutionException, InterruptedException, TimeoutException {
		return getAll(key, Object.class);
	}


	@Override
	@NonNull
	public <T> List<T> getAll(@NonNull Iterable<String> keys) throws ExecutionException, InterruptedException, TimeoutException {
		List<T> result = new ArrayList<>();
		Queue<RFuture<T>> futures = new LinkedList<>();
		Map<RFuture<T>, Integer> retryCounts = new HashMap<>();
		RBatch batch = template.createBatch();
		for (String key : keys) {
			RBucketAsync<T> bucket = batch.getBucket(key);
			RFuture<T> async = bucket.getAsync();
			futures.offer(async);
			retryCounts.put(async, 0);
		}
		batch.execute();
		while (!futures.isEmpty()) {
			RFuture<T> poll = futures.poll();
			CompletableFuture<T> completableFuture = poll.toCompletableFuture();
			if (completableFuture.isDone()) {
				try {
					result.add(completableFuture.getNow(null));
				} catch (CompletionException | CancellationException e) {
					throw new RuntimeException(e);
				}
			} else {
				log.warn("{} 进行重试", poll);
				int retryCount = retryCounts.get(poll);
				if (retryCount < 3) { // 如果重试次数小于 3 次
					retryCounts.put(poll, retryCount + 1); // 增加重试次数
					futures.offer(poll); // 重新加入队列
				} else {
					// 达到最大重试次数，处理错误
					throw new RuntimeException("Future " + poll + " failed after 3 retries");
				}
			}
		}
		return result;
	}

	@Override
	public <T> List<T> getAll(String key, Class<T> tClass) throws ExecutionException, InterruptedException, TimeoutException {
		if (!StringUtils.hasLength(key)) {
			throw new UtilException("key is not null");
		}
		RKeys rKeys = template.getKeys();
		Iterable<String> keysByPattern = rKeys.getKeysByPattern(key + "*");
		return getAll(keysByPattern);
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
	public void put(String key, Object value, Duration expireTime) {
		template.getBucket(key).set(value, expireTime.toSeconds(), TimeUnit.SECONDS);
	}

	public void batchPut(@NonNull Map<String, Object> map, @NonNull UnaryOperator<String> getKey, @NonNull Function<String, Duration> getExpire, @NonNull Consumer<Map.Entry<String, Object>> callback) {
		RBatch batch = template.createBatch();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			RBucketAsync<Object> bucket = batch.getBucket(getKey.apply(entry.getKey()));
			Duration expire = getExpire.apply(entry.getKey());
			if (expire.isZero()) {
				bucket.setAsync(entry.getValue());
			} else {
				bucket.setAsync(entry.getValue(), expire.getSeconds(), TimeUnit.SECONDS);
			}
			bucket.addListenerAsync(new SetObjectListener() {
				@Override
				public void onSet(String name) {
					System.out.println(name);
					callback.accept(entry);
				}
			});
		}
		batch.execute();
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

	public boolean tryLock(String lockName, long time, TimeUnit timeUnit) throws InterruptedException {
		RLock lock = template.getLock(lockName);
		return lock.tryLock(time, timeUnit);
	}

	public boolean tryLock(String lockName, long time) throws InterruptedException {

		return tryLock(lockName, time, TimeUnit.MILLISECONDS);
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

	@Override
	public void removeAll(String key) {
		RKeys rKeys = template.getKeys();
        for (String s : rKeys.getKeysByPattern(key + "*")) {
            template.getBucket(s).delete();
        }
	}

	@Override
	public void removeAll(Collection<String> keys) {
		keys.forEach(key -> template.getBucket(key).delete());
	}


}
