package com.ueueo.backgroundjobs;

import lombok.Data;

@Data
public class AbpBackgroundJobWorkerOptions {
    /**
     * Interval between polling jobs from <see cref="IBackgroundJobStore"/>.
     * Default value: 5000 (5 seconds).
     */
    private int jobPollPeriod;

    /**
     * Maximum count of jobs to fetch from data store in one loop.
     * Default: 1000.
     */
    private int maxJobFetchCount;

    /**
     * Default duration (as seconds) for the first wait on a failure.
     * Default value: 60 (1 minutes).
     */
    private int defaultFirstWaitDuration;

    /**
     * Default timeout value (as seconds) for a job before it's abandoned (<see cref="BackgroundJobInfo.IsAbandoned"/>).
     * Default value: 172,800 (2 days).
     */
    private int defaultTimeout;

    /**
     * Default wait factor for execution failures.
     * This amount is multiplated by last wait time to calculate next wait time.
     * Default value: 2.0.
     */
    private double DefaultWaitFactor;

    public AbpBackgroundJobWorkerOptions() {
        maxJobFetchCount = 1000;
        jobPollPeriod = 5000;
        defaultFirstWaitDuration = 60;
        defaultTimeout = 172800;
        DefaultWaitFactor = 2.0;
    }
}
