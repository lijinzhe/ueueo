package com.ueueo.backgroundworkers;

import com.ueueo.threading.CancellationToken;
import lombok.extern.slf4j.Slf4j;

/**
 * Base class that can be used to implement <see cref="IBackgroundWorker"/>.
 */
@Slf4j
public abstract class BackgroundWorkerBase implements IBackgroundWorker {
    //TODO: Add UOW, Localization and other useful properties..?

    protected CancellationToken stoppingToken;

    public BackgroundWorkerBase() {
        stoppingToken = new CancellationToken();
    }
    @Override
    public void start() {
        log.debug("Started background worker: " + this);
    }
    @Override
    public void stop() {
        log.debug("Stopped background worker: " + this);
        stoppingToken.cancel();
    }

    public CancellationToken getStoppingToken() {
        return stoppingToken;
    }

    @Override
    public String toString() {
        return getClass().getName();
    }
}
