package com.ueueo.backgroundjobs;

import com.ueueo.ID;
import lombok.Data;

import java.util.Date;

/**
 * Represents a background job info that is used to persist jobs.
 */
@Data
public class BackgroundJobInfo {
    private ID id;

    /**
     * Name of the job.
     */
    private String jobName;

    /**
     * Job arguments as serialized to string.
     */
    private String jobArgs;

    /**
     * Try count of this job.
     * A job is re-tried if it fails.
     */
    private int tryCount;

    /**
     * Creation time of this job.
     */
    private Date creationTime;

    /**
     * Next try time of this job.
     */
    private Date nextTryTime;

    /**
     * Last try time of this job.
     */
    private Date lastTryTime;

    /**
     * This is true if this job is continuously failed and will not be executed again.
     */
    private boolean isAbandoned;

    /**
     * Priority of this job.
     */
    private BackgroundJobPriority priority;

    /**
     * Initializes a new instance of the <see cref="BackgroundJobInfo"/> class.
     */
    public BackgroundJobInfo() {
        priority = BackgroundJobPriority.Normal;
    }
}
