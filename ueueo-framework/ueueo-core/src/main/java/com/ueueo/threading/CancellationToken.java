package com.ueueo.threading;

/**
 * @author Lee
 * @date 2022-05-29 15:39
 */
public class CancellationToken {
    private volatile boolean canceled = false;
    private volatile boolean isCancellationRequested = false;
    private volatile boolean canBeCanceled = true;

    public boolean isCanceled() {
        return canceled;
    }

    public boolean isCancellationRequested() {
        return isCancellationRequested;
    }

    public boolean canBeCanceled() {
        return canBeCanceled;
    }

    public synchronized void cancel() {
        this.canceled = true;
        this.isCancellationRequested = true;
        this.canBeCanceled = false;
    }
}
