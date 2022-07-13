package com.ueueo.eventbus.distributed;

import java.util.List;

public interface ISupportsEventBoxes {
    void publishFromOutbox(
            OutgoingEventInfo outgoingEvent,
            OutboxConfig outboxConfig
    );

    void publishManyFromOutbox(
            List<OutgoingEventInfo> outgoingEvents,
            OutboxConfig outboxConfig
    );

    void processFromInbox(
            IncomingEventInfo incomingEvent,
            InboxConfig inboxConfig
    );
}
