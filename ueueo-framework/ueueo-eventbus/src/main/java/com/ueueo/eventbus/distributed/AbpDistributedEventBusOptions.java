package com.ueueo.eventbus.distributed;

import com.ueueo.eventbus.IEventHandler;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AbpDistributedEventBusOptions {
    private final List<? extends Class<? extends IEventHandler>> handlers;

    private OutboxConfigDictionary outboxes;

    private InboxConfigDictionary inboxes;

    public AbpDistributedEventBusOptions() {
        handlers = new ArrayList<>();
        outboxes = new OutboxConfigDictionary();
        inboxes = new InboxConfigDictionary();
    }
}
