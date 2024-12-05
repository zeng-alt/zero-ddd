package com.zjj.autoconfigure.component.redis;

import org.redisson.api.RedissonClient;
import org.springframework.lang.NonNull;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年06月18日 18:56
 */
public abstract class RedisStringRepository extends RedisCrudRepository<String, Object> {

	public RedisStringRepository(RedissonClient template) {
		super(template);
	}


	public abstract void put(String key, Object value, Duration expireTime);

	public abstract void batchPut(@NonNull Map<String, Object> map, @NonNull UnaryOperator<String> getKey, @NonNull Function<String, Duration> getExpire, @NonNull Consumer<Map.Entry<String, Object>> callback);

	public abstract List<Object> getAll(String key) throws ExecutionException, InterruptedException, TimeoutException;

	public abstract <T> List<T> getAll(@NonNull Iterable<String> keys) throws ExecutionException, InterruptedException, TimeoutException;

	public abstract <T> List<T> getAll(String key, Class<T> tClass) throws ExecutionException, InterruptedException, TimeoutException;

	public abstract void put(String key, Object value, long expireTime);

	public abstract boolean putIfAbsent(String key, Object value, long expireTime);

	public abstract long increment(String key);

	public abstract long decrement(String key);

	public abstract boolean tryLock(String lockName);

	public abstract boolean tryLock(String lockName, long time, TimeUnit timeUnit) throws InterruptedException;

	public abstract boolean tryLock(String lockName, long time) throws InterruptedException;

	public abstract boolean tryLock(String lockName, long waitTime, long leaseTime) throws InterruptedException;

	public abstract void unlock(String lockName);

	public abstract void removeAll(String key);

	public abstract void removeAll(Collection<String> keys);
}