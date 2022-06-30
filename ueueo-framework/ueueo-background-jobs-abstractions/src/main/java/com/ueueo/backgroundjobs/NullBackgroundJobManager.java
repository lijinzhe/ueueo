package com.ueueo.backgroundjobs;

import com.ueueo.AbpException;
import com.ueueo.ID;

import java.util.concurrent.TimeUnit;

public class NullBackgroundJobManager implements IBackgroundJobManager {

    public NullBackgroundJobManager() {

    }

    @Override
    public ID enqueue(String jobName, Object args, BackgroundJobPriority priority, Integer delay, TimeUnit delayTimeUnit) {
        throw new AbpException("Background job system has not a real implementation. If it's mandatory, use an implementation (either the default provider or a 3rd party implementation). If it's optional, check IBackgroundJobManager.IsAvailable() extension method and act based on it.");
    }

}
