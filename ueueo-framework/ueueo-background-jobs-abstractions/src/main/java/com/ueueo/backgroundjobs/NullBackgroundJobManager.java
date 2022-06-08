package com.ueueo.backgroundjobs;

import com.ueueo.AbpException;

import java.util.concurrent.TimeUnit;

public class NullBackgroundJobManager implements IBackgroundJobManager<Object> {

    public NullBackgroundJobManager() {

    }

    @Override
    public String enqueue(Object o, com.ueueo.backgroundjobs.BackgroundJobPriority priority, Integer delay, TimeUnit delayTimeUnit) {
        throw new AbpException("Background job system has not a real implementation. If it's mandatory, use an implementation (either the default provider or a 3rd party implementation). If it's optional, check IBackgroundJobManager.IsAvailable() extension method and act based on it.");
    }

}
