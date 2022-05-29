package Abp.DistributedLocking;

import java.util.concurrent.Future;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-29 15:48
 */
public class LocalAbpDistributedLockHandle implements IAbpDistributedLockHandle{

    @Override
    public Future<?> disposeAsync() {
        return null;
    }
}
