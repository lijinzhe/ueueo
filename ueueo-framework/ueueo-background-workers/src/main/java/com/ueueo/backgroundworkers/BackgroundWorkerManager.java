package com.ueueo.backgroundworkers;

import com.ueueo.IDisposable;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements <see cref="IBackgroundWorkerManager"/>.
 */
public class BackgroundWorkerManager implements IBackgroundWorkerManager, IDisposable {
    protected boolean isRunning;

    private boolean isDisposed;

    private List<IBackgroundWorker> backgroundWorkers;

    /**
     * Initializes a new instance of the <see cref="BackgroundWorkerManager"/> class.
     */
    public BackgroundWorkerManager() {
        backgroundWorkers = new ArrayList<>();
    }

    @Override
    public void add(IBackgroundWorker worker) {
        backgroundWorkers.add(worker);

        if (isRunning) {
            worker.start();
        }
    }

    @Override
    public void dispose() {
        if (isDisposed) {
            return;
        }

        isDisposed = true;

        //TODO: ???
    }

    @Override
    public void start() {
        isRunning = true;

        for (IBackgroundWorker worker : backgroundWorkers) {
            worker.start();
        }
    }

    @Override
    public void stop() {
        isRunning = false;

        for (IBackgroundWorker worker : backgroundWorkers) {
            worker.stop();
        }
    }

    public boolean isRunning() {
        return isRunning;
    }
}
