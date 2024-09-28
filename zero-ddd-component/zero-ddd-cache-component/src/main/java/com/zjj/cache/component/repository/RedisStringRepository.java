package com.zjj.cache.component.repository;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年06月18日 18:56
 */
public abstract class RedisStringRepository extends RedisCrudRepository<String, String> {

    public abstract void put(String key, String value, long expireTime);

    public abstract boolean putIfAbsent(String key, String value, long expireTime);

    public abstract long increment(String key);

    public abstract long decrement(String key);

    public abstract boolean tryLock(String lockName);

    public abstract boolean lock(String lockName, long waitTime, long leaseTime) throws InterruptedException;

    public abstract void unlock(String lockName);

}
