using System;
using System.Collections.Generic;

namespace Volo.Abp.BackgroundJobs.RabbitMQ;

public class AbpRabbitMqBackgroundJobOptions
{
    /**
     * Key: Job Args Type
    */
    public Dictionary<Type, JobQueueConfiguration> JobQueues;//  { get; }

    /**
     * Default value: "AbpBackgroundJobs.".
    */
    public String DefaultQueueNamePrefix;// { get; set; }

    /**
     * Default value: "AbpBackgroundJobsDelayed."
    */
    public String DefaultDelayedQueueNamePrefix;// { get; set; }

    public AbpRabbitMqBackgroundJobOptions()
    {
        JobQueues = new Dictionary<Type, JobQueueConfiguration>();
        DefaultQueueNamePrefix = "AbpBackgroundJobs.";
        DefaultDelayedQueueNamePrefix = "AbpBackgroundJobsDelayed.";
    }
}
