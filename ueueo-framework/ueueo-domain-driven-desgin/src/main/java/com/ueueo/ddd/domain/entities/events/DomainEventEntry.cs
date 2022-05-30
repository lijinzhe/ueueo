using System;

namespace Volo.Abp.Domain.Entities.Events;

[Serializable]
public class DomainEventEntry
{
    public Object SourceEntity;//  { get; }

    public Object EventData;//  { get; }

    public long EventOrder;//  { get; }

    public DomainEventEntry(Object sourceEntity, Object eventData, long eventOrder)
    {
        SourceEntity = sourceEntity;
        EventData = eventData;
        EventOrder = eventOrder;
    }
}
