using System;

namespace Volo.Abp.Domain.Entities.Events;

/**
 * Used to pass data for an event when an entity (<see cref="IEntity"/>) is being changed (creating, updating or deleting).
 * See <see cref="EntityCreatingEventData{TEntity}"/>, <see cref="EntityDeletingEventData{TEntity}"/> and <see cref="EntityUpdatingEventData{TEntity}"/> classes.
*
 * <typeparam name="TEntity">Entity type</typeparam>
     */
[Serializable]
[Obsolete("This event is no longer needed and identical to EntityChangedEventData. Please use EntityChangedEventData instead.")]
public class EntityChangingEventData<TEntity> : EntityEventData<TEntity>
{
    /**
     * Constructor.
    *
     * <param name="entity">Changing entity in this event</param>
     */
    public EntityChangingEventData(TEntity entity)
        : base(entity)
    {

    }
}
