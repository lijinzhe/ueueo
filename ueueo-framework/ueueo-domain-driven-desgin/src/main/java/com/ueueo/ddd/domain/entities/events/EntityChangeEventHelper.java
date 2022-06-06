package com.ueueo.ddd.domain.entities.events;

import com.ueueo.ddd.domain.entities.events.distributed.AbpDistributedEntityEventOptions;
import com.ueueo.ddd.domain.entities.events.distributed.IEntityToEtoMapper;
import com.ueueo.eventbus.distributed.IDistributedEventBus;
import com.ueueo.eventbus.local.ILocalEventBus;
import com.ueueo.uow.IUnitOfWorkManager;
import lombok.extern.slf4j.Slf4j;

/**
 * Used to trigger entity change events.
*/
@Slf4j
public class EntityChangeEventHelper implements IEntityChangeEventHelper
{
    private final String UnitOfWorkEventRecordEntityPropName = "_Abp_Entity";

    public ILocalEventBus LocalEventBus;// { get; set; }
    public IDistributedEventBus DistributedEventBus;// { get; set; }

    protected IUnitOfWorkManager UnitOfWorkManager;//  { get; }
    protected IEntityToEtoMapper EntityToEtoMapper;//  { get; }
    protected AbpDistributedEntityEventOptions DistributedEntityEventOptions;//  { get; }

    public EntityChangeEventHelper(
        IUnitOfWorkManager unitOfWorkManager,
        IEntityToEtoMapper entityToEtoMapper,
        AbpDistributedEntityEventOptions distributedEntityEventOptions)
    {
        UnitOfWorkManager = unitOfWorkManager;
        EntityToEtoMapper = entityToEtoMapper;
        DistributedEntityEventOptions = distributedEntityEventOptions;

        LocalEventBus = NullLocalEventBus.Instance;
        DistributedEventBus = NullDistributedEventBus.Instance;
        Logger = NullLogger<EntityChangeEventHelper>.Instance;
    }

    public   void PublishEntityCreatingEvent(Object entity)
    {
        TriggerEventWithEntity(
            LocalEventBus,
#pragma warning disable 618
                typeof(EntityCreatingEventData<>),
#pragma warning restore 618
                entity,
            entity
        );
    }

    public   void PublishEntityCreatedEvent(Object entity)
    {
        TriggerEventWithEntity(
            LocalEventBus,
            typeof(EntityCreatedEventData<>),
            entity,
            entity
        );

        if (ShouldPublishDistributedEventForEntity(entity))
        {
            var eto = EntityToEtoMapper.Map(entity);
            if (eto != null)
            {
                TriggerEventWithEntity(
                    DistributedEventBus,
                    typeof(EntityCreatedEto<>),
                    eto,
                    entity
                );
            }
        }
    }

    private boolean ShouldPublishDistributedEventForEntity(Object entity)
    {
        return DistributedEntityEventOptions
            .AutoEventSelectors
            .IsMatch(
                ProxyHelper
                    .UnProxy(entity)
                    .GetType()
            );
    }

    public   void PublishEntityUpdatingEvent(Object entity)
    {
        TriggerEventWithEntity(
            LocalEventBus,
#pragma warning disable 618
                typeof(EntityUpdatingEventData<>),
#pragma warning restore 618
                entity,
            entity
        );
    }

    public   void PublishEntityUpdatedEvent(Object entity)
    {
        TriggerEventWithEntity(
            LocalEventBus,
            typeof(EntityUpdatedEventData<>),
            entity,
            entity
        );

        if (ShouldPublishDistributedEventForEntity(entity))
        {
            var eto = EntityToEtoMapper.Map(entity);
            if (eto != null)
            {
                TriggerEventWithEntity(
                    DistributedEventBus,
                    typeof(EntityUpdatedEto<>),
                    eto,
                    entity
                );
            }
        }
    }

    public   void PublishEntityDeletingEvent(Object entity)
    {
        TriggerEventWithEntity(
            LocalEventBus,
#pragma warning disable 618
                typeof(EntityDeletingEventData<>),
#pragma warning restore 618
                entity,
            entity
        );
    }

    public   void PublishEntityDeletedEvent(Object entity)
    {
        TriggerEventWithEntity(
            LocalEventBus,
            typeof(EntityDeletedEventData<>),
            entity,
            entity
        );

        if (ShouldPublishDistributedEventForEntity(entity))
        {
            var eto = EntityToEtoMapper.Map(entity);
            if (eto != null)
            {
                TriggerEventWithEntity(
                    DistributedEventBus,
                    typeof(EntityDeletedEto<>),
                    eto,
                    entity
                );
            }
        }
    }

    protected   void TriggerEventWithEntity(
        IEventBus eventPublisher,
        Type genericEventType,
        Object entityOrEto,
        Object originalEntity)
    {
        var entityType = ProxyHelper.UnProxy(entityOrEto).GetType();
        var eventType = genericEventType.MakeGenericType(entityType);
        var eventData = Activator.CreateInstance(eventType, entityOrEto);
        var currentUow = UnitOfWorkManager.Current;

        if (currentUow == null)
        {
            Logger.LogWarning("UnitOfWorkManager.Current is null! Can not publish the event.");
            return;
        }

        var eventRecord = new UnitOfWorkEventRecord(eventType, eventData, EventOrderGenerator.GetNext())
        {
            Properties =
                {
                    { UnitOfWorkEventRecordEntityPropName, originalEntity },
                }
        };

        /* We are trying to eliminate same events for the same entity.
         * In this way, for example, we don't trigger update event for an entity multiple times
         * even if it is updated multiple times in the current UOW.
         */

        if (eventPublisher == DistributedEventBus)
        {
            currentUow.AddOrReplaceDistributedEvent(
                eventRecord,
                otherRecord => IsSameEntityEventRecord(eventRecord, otherRecord)
            );
        }
        else
        {
            currentUow.AddOrReplaceLocalEvent(
                eventRecord,
                otherRecord => IsSameEntityEventRecord(eventRecord, otherRecord)
            );
        }
    }

    public boolean IsSameEntityEventRecord(UnitOfWorkEventRecord record1, UnitOfWorkEventRecord record2)
    {
        if (record1.EventType != record2.EventType)
        {
            return false;
        }

        var record1OriginalEntity = record1.Properties.GetOrDefault(UnitOfWorkEventRecordEntityPropName) as IEntity;
        var record2OriginalEntity = record2.Properties.GetOrDefault(UnitOfWorkEventRecordEntityPropName) as IEntity;

        if (record1OriginalEntity == null || record2OriginalEntity == null)
        {
            return false;
        }

        return EntityHelper.EntityEquals(record1OriginalEntity, record2OriginalEntity);
    }
}
