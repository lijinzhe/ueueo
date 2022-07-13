package com.ueueo.eventbus.distributed;

import com.ueueo.ID;
import com.ueueo.threading.CancellationToken;

import java.util.Collection;
import java.util.List;

public interface IEventOutbox
{
    void enqueue(OutgoingEventInfo outgoingEvent);

    List<OutgoingEventInfo> getWaitingEvents(int maxCount, CancellationToken cancellationToken);

    void delete(ID id);

    void deleteMany(Collection<ID> ids);
}
