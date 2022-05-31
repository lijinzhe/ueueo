using System;

namespace Volo.Abp.BackgroundJobs;

/**
 * Represents a background job info that is used to persist jobs.
*/
public class BackgroundJobInfo
{
    public ID Id;// { get; set; }

    /**
     * Name of the job.
    */
    public   String JobName;// { get; set; }

    /**
     * Job arguments as serialized to string.
    */
    public   String JobArgs;// { get; set; }

    /**
     * Try count of this job.
     * A job is re-tried if it fails.
    */
    public   short TryCount;// { get; set; }

    /**
     * Creation time of this job.
    */
    public   Date CreationTime;// { get; set; }

    /**
     * Next try time of this job.
    */
    public   Date NextTryTime;// { get; set; }

    /**
     * Last try time of this job.
    */
    public   DateTime? LastTryTime;// { get; set; }

    /**
     * This is true if this job is continuously failed and will not be executed again.
    */
    public   boolean IsAbandoned;// { get; set; }

    /**
     * Priority of this job.
    */
    public   BackgroundJobPriority Priority;// { get; set; }

    /**
     * Initializes a new instance of the <see cref="BackgroundJobInfo"/> class.
    */
    public BackgroundJobInfo()
    {
        Priority = BackgroundJobPriority.Normal;
    }
}
