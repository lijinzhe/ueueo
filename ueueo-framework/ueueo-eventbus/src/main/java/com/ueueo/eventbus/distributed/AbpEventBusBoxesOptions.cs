using System;

namespace Volo.Abp.EventBus.Boxes;

public class AbpEventBusBoxesOptions
{
    /**
     * Default: 6 hours
    */
    public TimeSpan CleanOldEventTimeIntervalSpan;// { get; set; }

    /**
     * Default: 1000
    */
    public int InboxWaitingEventMaxCount;// { get; set; }

    /**
     * Default: 1000
    */
    public int OutboxWaitingEventMaxCount;// { get; set; }

    /**
     * Period time of <see cref="InboxProcessor"/> and <see cref="OutboxSender"/>
     * Default: 2 seconds
    */
    public TimeSpan PeriodTimeSpan;// { get; set; }

    /**
     * Default: 15 seconds
    */
    public TimeSpan DistributedLockWaitDuration;// { get; set; }

    /**
     * Default: 2 hours
    */
    public TimeSpan WaitTimeToDeleteProcessedInboxEvents;// { get; set; }

    /**
     * Default: true
    */
    public boolean BatchPublishOutboxEvents;// { get; set; }

    public AbpEventBusBoxesOptions()
    {
        CleanOldEventTimeIntervalSpan = TimeSpan.FromHours(6);
        InboxWaitingEventMaxCount = 1000;
        OutboxWaitingEventMaxCount = 1000;
        PeriodTimeSpan = TimeSpan.FromSeconds(2);
        DistributedLockWaitDuration = TimeSpan.FromSeconds(15);
        WaitTimeToDeleteProcessedInboxEvents = TimeSpan.FromHours(2);
        BatchPublishOutboxEvents = true;
    }
}
