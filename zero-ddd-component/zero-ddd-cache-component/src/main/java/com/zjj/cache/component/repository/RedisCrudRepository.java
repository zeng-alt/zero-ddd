package com.zjj.cache.component.repository;

import lombok.Getter;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.time.Duration;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年06月18日 18:56
 */
@Getter
public abstract class RedisCrudRepository<K, V> {

	protected final RedissonClient template;

    protected RedisCrudRepository(RedissonClient template) {
        this.template = template;
    }

	public abstract <T> T get(String key);

	public abstract void put(K key, V value);

	public abstract void putIfAbsent(K key, V value);

	public long getTimeToLive(final String key) {
		RBucket<V> rBucket = template.getBucket(key);
		return rBucket.remainTimeToLive();
	}

	/**
	 * 设置过期时间。
	 * @param key 键
	 * @param expireTime 过期时间（秒）
	 */
	public void setExpireForExistingKey(String key, long expireTime) {
		RBucket<String> bucket = template.getBucket(key);
		bucket.expire(Duration.ofSeconds(expireTime));
	}

	public boolean containsKey(K key) {
		return Boolean.TRUE.equals(template.getBucket(key.toString()).isExists());
	}

	public void remove(K key) {
		template.getBucket(key.toString()).delete();
	}

}
