package com.ueueo.eventbus.distributed;

import com.ueueo.ID;
import com.ueueo.threading.CancellationToken;

import java.util.List;

public interface IEventInbox {

    void enqueue(IncomingEventInfo incomingEvent);

    List<IncomingEventInfo> getWaitingEventsAsyn(int maxCount, CancellationToken cancellationToken);

    void markAsProcessed(ID id);

    Boolean existsByMessageId(String messageId);

    void deleteOldEvents();
}
