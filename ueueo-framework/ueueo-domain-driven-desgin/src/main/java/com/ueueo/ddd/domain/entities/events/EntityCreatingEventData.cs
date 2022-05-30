using System;

namespace Volo.Abp.Domain.Entities.Events;

/**
 * This type of event is used to notify just before creation of an Entity.
*
 * <typeparam name="TEntity">Entity type</typeparam>
     */
[Serializable]
[Obsolete("This event is no longer needed and identical to EntityCreatedEventData. Please use EntityCreatedEventData instead.")]
public class EntityCreatingEventData<TEntity> : EntityChangingEventData<TEntity>
{
    /**
     * Constructor.
    *
     * <param name="entity">The entity which is being created</param>
     */
    public EntityCreatingEventData(TEntity entity)
        : base(entity)
    {

    }
}
