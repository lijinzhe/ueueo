package com.ueueo.backgroundjobs;

import java.util.concurrent.Future;

/**
 * Defines interface of a background job.
 *
 * @author Lee
 * @date 2022-05-29 18:20
 */
public interface IAsyncBackgroundJob<TArgs> {

    /**
     * Executes the job with the <paramref name="args"/>.
     *
     * @param args Job arguments.
     *
     * @return
     */
    Future<?> executeAsync(TArgs args);
}
