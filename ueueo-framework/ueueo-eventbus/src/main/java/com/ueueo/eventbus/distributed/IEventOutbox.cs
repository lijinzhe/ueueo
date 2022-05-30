using System;
using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;

namespace Volo.Abp.EventBus.Distributed;

public interface IEventOutbox
{
    void EnqueueAsync(OutgoingEventInfo outgoingEvent);

    Task<List<OutgoingEventInfo>> GetWaitingEventsAsync(int maxCount, CancellationToken cancellationToken = default);

    void DeleteAsync(Guid id);

    void DeleteManyAsync(IEnumerable<Guid> ids);
}
