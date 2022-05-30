using System.Collections.Generic;
using System.Threading.Tasks;

namespace Volo.Abp.EventBus.Distributed;

public interface ISupportsEventBoxes
{
    void PublishFromOutboxAsync(
        OutgoingEventInfo outgoingEvent,
        OutboxConfig outboxConfig
    );

    void PublishManyFromOutboxAsync(
        IEnumerable<OutgoingEventInfo> outgoingEvents,
        OutboxConfig outboxConfig
    );

    void ProcessFromInboxAsync(
        IncomingEventInfo incomingEvent,
        InboxConfig inboxConfig
    );
}
