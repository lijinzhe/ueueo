package com.ueueo.eventbus.distributed;

import lombok.Data;

import java.time.Duration;

@Data
public class AbpEventBusBoxesOptions {
    /**
     * Default: 6 hours
     */
    private Duration cleanOldEventTimeIntervalSpan = Duration.ofHours(6);

    /**
     * Default: 1000
     */
    private int inboxWaitingEventMaxCount = 1000;

    /**
     * Default: 1000
     */
    private int outboxWaitingEventMaxCount = 1000;

    /**
     * Period time of <see cref="InboxProcessor"/> and <see cref="OutboxSender"/>
     * Default: 2 seconds
     */
    private Duration periodTimeSpan = Duration.ofSeconds(2);

    /**
     * Default: 15 seconds
     */
    private Duration distributedLockWaitDuration = Duration.ofSeconds(15);

    /**
     * Default: 2 hours
     */
    private Duration waitTimeToDeleteProcessedInboxEvents = Duration.ofHours(2);

    /**
     * Default: true
     */
    private boolean batchPublishOutboxEvents = true;

    public AbpEventBusBoxesOptions() {
    }
}
