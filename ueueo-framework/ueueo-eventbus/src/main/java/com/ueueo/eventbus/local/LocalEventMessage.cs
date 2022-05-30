using System;

namespace Volo.Abp.EventBus.Local;

public class LocalEventMessage
{
    public ID MessageId;//  { get; }

    public Object EventData;//  { get; }

    public Type EventType;//  { get; }

    public LocalEventMessage(Guid messageId, Object eventData, Type eventType)
    {
        MessageId = messageId;
        EventData = eventData;
        EventType = eventType;
    }
}
