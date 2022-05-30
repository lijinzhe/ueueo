using System;
using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;

namespace Volo.Abp.EventBus.Distributed;

public interface IEventInbox
{
    void EnqueueAsync(IncomingEventInfo incomingEvent);

    Task<List<IncomingEventInfo>> GetWaitingEventsAsync(int maxCount, CancellationToken cancellationToken = default);

    void MarkAsProcessedAsync(Guid id);

    Task<bool> ExistsByMessageIdAsync(String messageId);

    void DeleteOldEventsAsync();
}
