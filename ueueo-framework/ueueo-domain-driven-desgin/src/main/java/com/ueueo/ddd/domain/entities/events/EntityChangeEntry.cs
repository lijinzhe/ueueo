using System;
using Volo.Abp.Auditing;

namespace Volo.Abp.Domain.Entities.Events;

[Serializable]
public class EntityChangeEntry
{
    public Object Entity;// { get; set; }

    public EntityChangeType ChangeType;// { get; set; }

    public EntityChangeEntry(Object entity, EntityChangeType changeType)
    {
        Entity = entity;
        ChangeType = changeType;
    }
}
