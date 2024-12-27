package com.zjj.autoconfigure.component.redis;

import java.util.concurrent.TimeUnit;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月27日 21:16
 */
public interface Lock {

    public boolean tryLock(String lockName);

    public boolean tryLock(String lockName, long time, TimeUnit timeUnit) throws InterruptedException;

    public boolean tryLock(String lockName, long time) throws InterruptedException;

    public boolean tryLock(String lockName, long waitTime, long leaseTime) throws InterruptedException;

    public void unlock(String lockName);
}
