using System;

namespace Volo.Abp.Domain.Entities.Events;

/**
 * This type of event can be used to notify just after deletion of an Entity.
*
 * <typeparam name="TEntity">Entity type</typeparam>
     */
[Serializable]
public class EntityDeletedEventData<TEntity> : EntityChangedEventData<TEntity>
{
    /**
     * Constructor.
    *
     * <param name="entity">The entity which is deleted</param>
     */
    public EntityDeletedEventData(TEntity entity)
        : base(entity)
    {

    }
}
