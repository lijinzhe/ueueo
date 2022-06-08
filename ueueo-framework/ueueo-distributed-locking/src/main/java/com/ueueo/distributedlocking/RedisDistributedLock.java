package com.ueueo.distributedlocking;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.lang.NonNull;

import java.util.concurrent.TimeUnit;

/**
 * @author Lee
 * @date 2022-06-08 15:57
 */
public class RedisDistributedLock implements IDistributedLock {

    private RedissonClient redissonClient;

    public RedisDistributedLock(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public IDistributedLockHandle tryAcquire(@NonNull String name, Integer timeout, TimeUnit timeUnit) {
        RLock lock = redissonClient.getLock(name);
        try {
            if (lock.tryLock(0, timeout, timeUnit)) {
                return new RedisDistributedLockHandle(lock);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
