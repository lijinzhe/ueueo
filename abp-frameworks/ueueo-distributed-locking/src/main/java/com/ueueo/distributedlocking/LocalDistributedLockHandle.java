package com.ueueo.distributedlocking;

import java.util.concurrent.locks.Lock;

/**
 * @author Lee
 * @date 2022-05-29 15:48
 */
public class LocalDistributedLockHandle implements IDistributedLockHandle {

    private Lock lock;

    public LocalDistributedLockHandle(Lock lock) {
        this.lock = lock;
    }

    @Override
    public void dispose() {
        lock.unlock();
    }
}
