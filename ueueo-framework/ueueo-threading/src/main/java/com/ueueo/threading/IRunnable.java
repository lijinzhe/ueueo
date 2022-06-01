package com.ueueo.threading;

/**
 * Interface to start/stop self threaded services.
 */
public interface IRunnable {
    /**
     * Starts the service.
     */
    void startAsync(CancellationToken cancellationToken);

    /**
     * Stops the service.
     */
    void stopAsync(CancellationToken cancellationToken);
}
