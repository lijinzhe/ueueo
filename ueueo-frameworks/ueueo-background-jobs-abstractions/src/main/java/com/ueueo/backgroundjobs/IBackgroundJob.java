package com.ueueo.backgroundjobs;

/**
 * @author Lee
 * @date 2022-05-29 18:14
 */
public interface IBackgroundJob<TArgs> {

    /**
     * Executes the job with the <paramref name="args"/>.
     *
     * @param args Job arguments.
     */
    void execute(TArgs args);
}
