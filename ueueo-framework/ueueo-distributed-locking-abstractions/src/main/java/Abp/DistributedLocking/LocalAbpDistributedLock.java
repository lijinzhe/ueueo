package Abp.DistributedLocking;

import com.ueueo.threading.CancellationToken;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-29 15:46
 */
public class LocalAbpDistributedLock implements IAbpDistributedLock {

    @Override
    public IAbpDistributedLockHandle tryAcquire(String name, Integer timeout, TimeUnit timeUnit, CancellationToken cancellationToken) {
        Objects.requireNonNull(name);

        return null;
    }

}
