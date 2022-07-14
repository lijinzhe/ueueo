package com.ueueo.distributedlocking.redis;

import com.ueueo.distributedlocking.IDistributedLockHandle;
import org.redisson.api.RLock;

public class RedisDistributedLockHandle implements IDistributedLockHandle {

    private RLock lock;

    public RedisDistributedLockHandle(RLock lock) {
        this.lock = lock;
    }

    @Override
    public void dispose() {
        lock.unlock();
    }
}
