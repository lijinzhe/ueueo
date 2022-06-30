package com.ueueo.distributedlocking;

import org.springframework.lang.NonNull;

import java.time.Duration;

/**
 * @author Lee
 * @date 2022-05-29 15:35
 */
public interface IDistributedLock {

    /**
     * Tries to acquire a named lock.
     * Returns a disposable object to release the lock.
     * It is suggested to use this method within a using block.
     * Returns null if the lock could not be handled.
     *
     * @param name              The name of the lock
     * @param timeout           Timeout value
     *
     * @return
     */
    IDistributedLockHandle tryAcquire(
            @NonNull String name,
            Duration timeout
    );
}
