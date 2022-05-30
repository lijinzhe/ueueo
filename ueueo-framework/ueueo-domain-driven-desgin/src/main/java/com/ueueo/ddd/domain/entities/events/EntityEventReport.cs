using System.Collections.Generic;

namespace Volo.Abp.Domain.Entities.Events;

public class EntityEventReport
{
    public List<DomainEventEntry> DomainEvents;//  { get; }

    public List<DomainEventEntry> DistributedEvents;//  { get; }

    public EntityEventReport()
    {
        DomainEvents = new List<DomainEventEntry>();
        DistributedEvents = new List<DomainEventEntry>();
    }

    @Override public String toString()
    {
        return $"[{nameof(EntityEventReport)}] DomainEvents: {DomainEvents.Count}, DistributedEvents: {DistributedEvents.Count}";
    }
}
