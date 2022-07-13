package com.ueueo.threading;

/**
 * Interface to start/stop self threaded services.
 */
public interface IRunnable {
    /**
     * Starts the service.
     */
    void start();

    /**
     * Stops the service.
     */
    void stop();
}
