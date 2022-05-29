package com.ueueo.backgroundjobs;

import java.util.concurrent.TimeUnit;

/**
 * Defines interface of a job manager.
 *
 * @param <TArgs> Type of the arguments of job.
 *
 * @author Lee
 * @date 2022-05-29 18:15
 */
public interface IBackgroundJobManager<TArgs> {
    /**
     * Enqueues a job to be executed.
     *
     * @param args          Job arguments.
     * @param priority      Job priority. default Normal
     * @param delay         Job delay (wait duration before first try).
     * @param delayTimeUnit 时间单位
     *
     * @return
     */
    String enqueue(TArgs args, BackgroundJobPriority priority, Integer delay, TimeUnit delayTimeUnit);
}
