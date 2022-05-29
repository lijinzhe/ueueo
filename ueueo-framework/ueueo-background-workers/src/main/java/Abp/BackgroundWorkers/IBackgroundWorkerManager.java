package Abp.BackgroundWorkers;

/**
 * Used to manage background workers.
 *
 * @author Lee
 * @date 2022-05-29 18:10
 */
public interface IBackgroundWorkerManager extends Runnable {

    /**
     * Adds a new worker. Starts the worker immediately if <see cref="IBackgroundWorkerManager"/> has started.
     *
     * @param worker The worker. It should be resolved from IOC.
     */
    void add(IBackgroundWorker worker);
}
