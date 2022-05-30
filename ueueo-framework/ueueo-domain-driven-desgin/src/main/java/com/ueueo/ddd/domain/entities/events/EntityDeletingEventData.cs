using System;

namespace Volo.Abp.Domain.Entities.Events;

/**
 * This type of event is used to notify just before deletion of an Entity.
*
 * <typeparam name="TEntity">Entity type</typeparam>
     */
[Serializable]
[Obsolete("This event is no longer needed and identical to EntityDeleteEventData. Please use EntityDeleteEventData instead.")]
public class EntityDeletingEventData<TEntity> : EntityChangingEventData<TEntity>
{
    /**
     * Constructor.
     *
     * <param name="entity">The entity which is being deleted</param>
     */
    public EntityDeletingEventData(TEntity entity)
        : base(entity)
    {

    }
}
