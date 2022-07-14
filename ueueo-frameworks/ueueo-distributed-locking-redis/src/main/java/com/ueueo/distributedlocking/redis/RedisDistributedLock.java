package com.ueueo.distributedlocking.redis;

import com.ueueo.distributedlocking.IDistributedLock;
import com.ueueo.distributedlocking.IDistributedLockHandle;
import com.ueueo.util.Check;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.lang.NonNull;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author Lee
 * @date 2022-06-08 15:57
 */
public class RedisDistributedLock implements IDistributedLock {

    private final RedissonClient redissonClient;

    public RedisDistributedLock(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public IDistributedLockHandle tryAcquire(@NonNull String name, Duration duration) {
        Check.notNullOrEmpty(name, "name");
        RLock lock = redissonClient.getLock(name);
        try {
            if (lock.tryLock(0, duration.getSeconds(), TimeUnit.SECONDS)) {
                return new RedisDistributedLockHandle(lock);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
