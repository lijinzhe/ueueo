package Abp.DistributedLocking;

import com.ueueo.threading.CancellationToken;
import org.springframework.lang.NonNull;

import java.util.concurrent.TimeUnit;

/**
 * @author Lee
 * @date 2022-05-29 15:35
 */
public interface IAbpDistributedLock {

    /**
     * Tries to acquire a named lock.
     * Returns a disposable object to release the lock.
     * It is suggested to use this method within a using block.
     * Returns null if the lock could not be handled.
     *
     * @param name              The name of the lock
     * @param timeout           Timeout value
     * @param timeUnit
     * @param cancellationToken Cancellation token
     *
     * @return
     */
    IAbpDistributedLockHandle tryAcquire(
            @NonNull String name,
            Integer timeout,
            TimeUnit timeUnit,
            CancellationToken cancellationToken
    );
}
