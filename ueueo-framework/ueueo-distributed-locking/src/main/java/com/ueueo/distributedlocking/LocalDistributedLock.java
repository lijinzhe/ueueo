package com.ueueo.distributedlocking;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Lee
 * @date 2022-05-29 15:46
 */
public class LocalDistributedLock implements IDistributedLock {

    private final ConcurrentHashMap<String, ReentrantLock> localSyncObjects = new ConcurrentHashMap<>();

    @Override
    public IDistributedLockHandle tryAcquire(String name, Integer timeout, TimeUnit timeUnit) {
        Objects.requireNonNull(name);
        ReentrantLock lock = localSyncObjects.computeIfAbsent(name, s -> new ReentrantLock(true));
        try {
            if (lock.tryLock(timeout, timeUnit)) {
                return new LocalDistributedLockHandle(lock);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
