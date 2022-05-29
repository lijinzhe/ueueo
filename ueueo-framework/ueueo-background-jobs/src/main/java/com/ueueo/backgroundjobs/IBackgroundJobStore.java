package com.ueueo.backgroundjobs;

import com.ueueo.ID;

import java.util.List;

/**
 * Defines interface to store/get background jobs.
 *
 * @author Lee
 * @date 2022-05-29 18:05
 */
public interface IBackgroundJobStore {

    /**
     * Gets a BackgroundJobInfo based on the given jobId.
     *
     * @param jobId The Job Unique Identifier.
     *
     * @return The BackgroundJobInfo object.
     */
    BackgroundJobInfo find(ID jobId);

    /**
     * Inserts a background job.
     *
     * @param jobInfo Job information.
     */
    void insert(BackgroundJobInfo jobInfo);

    /**
     * Gets waiting jobs. It should get jobs based on these:
     * Conditions: !IsAbandoned And NextTryTime &lt;= Clock.Now.
     * Order by: Priority DESC, TryCount ASC, NextTryTime ASC.
     * Maximum result: maxResultCount
     *
     * @param maxResultCount Maximum result count.
     *
     * @return
     */
    List<BackgroundJobInfo> getWaitingJobs(int maxResultCount);

    /**
     * Deletes a job.
     *
     * @param jobId The Job Unique Identifier.
     */
    void delete(ID jobId);

    /**
     * Updates a job.
     *
     * @param jobInfo Job information.
     */
    void update(BackgroundJobInfo jobInfo);
}
