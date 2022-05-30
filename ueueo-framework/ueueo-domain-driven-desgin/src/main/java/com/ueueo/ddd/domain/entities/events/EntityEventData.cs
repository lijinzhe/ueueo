using System;
using Volo.Abp.EventBus;
using Volo.Abp.MultiTenancy;

namespace Volo.Abp.Domain.Entities.Events;

/**
 * Used to pass data for an event that is related to with an <see cref="IEntity"/> object.
*
 * <typeparam name="TEntity">Entity type</typeparam>
     */
[Serializable]
public class EntityEventData<TEntity> : IEventDataWithInheritableGenericArgument, IEventDataMayHaveTenantId
{
    /**
     * Related entity with this event.
    */
    public TEntity Entity;//  { get; }

    /**
     * Constructor.
    *
     * <param name="entity">Related entity with this event</param>
     */
    public EntityEventData(TEntity entity)
    {
        Entity = entity;
    }

    public   Object[] GetConstructorArgs()
    {
        return new Object[] { Entity };
    }

    public   boolean IsMultiTenant(out ID tenantId)
    {
        if (Entity is IMultiTenant multiTenantEntity)
        {
            tenantId = multiTenantEntity.TenantId;
            return true;
        }

        tenantId = null;
        return false;
    }
}
