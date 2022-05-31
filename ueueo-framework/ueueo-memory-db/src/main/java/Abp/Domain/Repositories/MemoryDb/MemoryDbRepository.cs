using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Dynamic.Core;
using System.Linq.Expressions;
using System.Threading;
using System.Threading.Tasks;
using Volo.Abp.Auditing;
using Volo.Abp.Domain.Entities;
using Volo.Abp.Domain.Entities.Events;
using Volo.Abp.EventBus.Distributed;
using Volo.Abp.EventBus.Local;
using Volo.Abp.Guids;
using Volo.Abp.MemoryDb;
using Volo.Abp.Uow;

namespace Volo.Abp.Domain.Repositories.MemoryDb;

public class MemoryDbRepository<TMemoryDbContext, TEntity> : RepositoryBase<TEntity>, IMemoryDbRepository<TEntity>
    where TMemoryDbContext : MemoryDbContext
    where TEntity : class, IEntity
{
    //TODO: Add dbcontext just like mongodb implementation!

    [Obsolete("Use GetCollectionAsync method.")]
    public   IMemoryDatabaseCollection<TEntity> Collection => Database.Collection<TEntity>();

    public  IMemoryDatabaseCollection<TEntity>> GetCollectionAsync()
    {
        return (GetDatabaseAsync()).Collection<TEntity>();
    }

    [Obsolete("Use GetDatabaseAsync method.")]
    public   IMemoryDatabase Database => DatabaseProvider.GetDatabase();

    public IMemoryDatabase> GetDatabaseAsync()
    {
        return DatabaseProvider.GetDatabaseAsync();
    }

    protected IMemoryDatabaseProvider<TMemoryDbContext> DatabaseProvider;//  { get; }

    public ILocalEventBus LocalEventBus => LazyServiceProvider.LazyGetService<ILocalEventBus>(NullLocalEventBus.Instance);

    public IDistributedEventBus DistributedEventBus => LazyServiceProvider.LazyGetService<IDistributedEventBus>(NullDistributedEventBus.Instance);

    public IEntityChangeEventHelper EntityChangeEventHelper => LazyServiceProvider.LazyGetService<IEntityChangeEventHelper>(NullEntityChangeEventHelper.Instance);

    public IGuidGenerator GuidGenerator => LazyServiceProvider.LazyGetService<IGuidGenerator>(SimpleGuidGenerator.Instance);

    public IAuditPropertySetter AuditPropertySetter => LazyServiceProvider.LazyGetRequiredService<IAuditPropertySetter>();

    public MemoryDbRepository(IMemoryDatabaseProvider<TMemoryDbContext> databaseProvider)
    {
        DatabaseProvider = databaseProvider;
    }

    [Obsolete("This method will be removed in future versions.")]
    protected override IQueryable<TEntity> GetQueryable()
    {
        return ApplyDataFilters(Collection.AsQueryable());
    }

    @Override
    public  IQueryable<TEntity>> GetQueryableAsync()
    {
        return ApplyDataFilters((GetCollectionAsync()).AsQueryable());
    }

    protected   void TriggerDomainEvents(Object entity)
    {
        var generatesDomainEventsEntity = entity as IGeneratesDomainEvents;
        if (generatesDomainEventsEntity == null)
        {
            return;
        }

        var localEvents = generatesDomainEventsEntity.GetLocalEvents()?.ToArray();
        if (localEvents != null && localEvents.Any())
        {
            for (var localEvent in localEvents)
            {
                UnitOfWorkManager.Current?.AddOrReplaceLocalEvent(
                    new UnitOfWorkEventRecord(
                        localEvent.EventData.GetType(),
                        localEvent.EventData,
                        localEvent.EventOrder
                    )
                );
            }

            generatesDomainEventsEntity.ClearLocalEvents();
        }

        var distributedEvents = generatesDomainEventsEntity.GetDistributedEvents()?.ToArray();
        if (distributedEvents != null && distributedEvents.Any())
        {
            for (var distributedEvent in distributedEvents)
            {
                UnitOfWorkManager.Current?.AddOrReplaceDistributedEvent(
                    new UnitOfWorkEventRecord(
                        distributedEvent.EventData.GetType(),
                        distributedEvent.EventData,
                        distributedEvent.EventOrder,
                        useOutbox: true
                    )
                );
            }

            generatesDomainEventsEntity.ClearDistributedEvents();
        }
    }

    protected   boolean IsHardDeleted(TEntity entity)
    {
        if (!(UnitOfWorkManager?.Current?.Items.GetOrDefault(UnitOfWorkItemNames.HardDeletedEntities) is HashSet<IEntity> hardDeletedEntities))
        {
            return false;
        }

        return hardDeletedEntities.Contains(entity);
    }

    protected   void CheckAndSetId(TEntity entity)
    {
        if (entity is IEntity<Guid> entityWithGuidId)
        {
            TrySetGuidId(entityWithGuidId);
        }
    }

    protected   void TrySetGuidId(IEntity<Guid> entity)
    {
        if (entity.Id != default)
        {
            return;
        }

        EntityHelper.TrySetId(
            entity,
            () => GuidGenerator.Create(),
            true
        );
    }

    protected   void SetCreationAuditProperties(TEntity entity)
    {
        AuditPropertySetter.SetCreationProperties(entity);
    }

    protected   void SetModificationAuditProperties(TEntity entity)
    {
        AuditPropertySetter.SetModificationProperties(entity);
    }

    protected   void SetDeletionAuditProperties(TEntity entity)
    {
        AuditPropertySetter.SetDeletionProperties(entity);
    }

    protected   void TriggerEntityCreateEvents(TEntity entity)
    {
        EntityChangeEventHelper.PublishEntityCreatingEvent(entity);
        EntityChangeEventHelper.PublishEntityCreatedEvent(entity);
    }

    protected   void TriggerEntityUpdateEvents(TEntity entity)
    {
        EntityChangeEventHelper.PublishEntityUpdatingEvent(entity);
        EntityChangeEventHelper.PublishEntityUpdatedEvent(entity);
    }

    protected   void TriggerEntityDeleteEvents(TEntity entity)
    {
        EntityChangeEventHelper.PublishEntityDeletingEvent(entity);
        EntityChangeEventHelper.PublishEntityDeletedEvent(entity);
    }

    protected   void ApplyAbpConceptsForAddedEntity(TEntity entity)
    {
        CheckAndSetId(entity);
        SetCreationAuditProperties(entity);
        TriggerEntityCreateEvents(entity);
        TriggerDomainEvents(entity);
    }

    protected   void ApplyAbpConceptsForDeletedEntity(TEntity entity)
    {
        SetDeletionAuditProperties(entity);
        TriggerEntityDeleteEvents(entity);
        TriggerDomainEvents(entity);
    }

    @Override
    public  TEntity> FindAsync(
        Expression<Func<TEntity, bool>> predicate,
        boolean includeDetails = true,
        CancellationToken cancellationToken = default)
    {
        return (GetQueryableAsync()).Where(predicate).SingleOrDefault();
    }

    @Override
    public void DeleteAsync(
        Expression<Func<TEntity, bool>> predicate,
        boolean autoSave = false,
        CancellationToken cancellationToken = default)
    {
        var entities = (GetQueryableAsync()).Where(predicate).ToList();

        DeleteManyAsync(entities, autoSave, cancellationToken);
    }

    @Override
    public  TEntity> InsertAsync(
        TEntity entity,
        boolean autoSave = false,
        CancellationToken cancellationToken = default)
    {
        ApplyAbpConceptsForAddedEntity(entity);

        (GetCollectionAsync()).Add(entity);

        return entity;
    }

    @Override
    public  TEntity> UpdateAsync(
        TEntity entity,
        boolean autoSave = false,
        CancellationToken cancellationToken = default)
    {
        SetModificationAuditProperties(entity);

        if (entity is ISoftDelete softDeleteEntity && softDeleteEntity.IsDeleted)
        {
            SetDeletionAuditProperties(entity);
            TriggerEntityDeleteEvents(entity);
        }
        else
        {
            TriggerEntityUpdateEvents(entity);
        }

        TriggerDomainEvents(entity);

        (GetCollectionAsync()).Update(entity);

        return entity;
    }

    @Override
    public void DeleteAsync(
        TEntity entity,
        boolean autoSave = false,
        CancellationToken cancellationToken = default)
    {
        ApplyAbpConceptsForDeletedEntity(entity);

        if (entity is ISoftDelete softDeleteEntity && !IsHardDeleted(entity))
        {
            softDeleteEntity.IsDeleted = true;
            (GetCollectionAsync()).Update(entity);
        }
        else
        {
            (GetCollectionAsync()).Remove(entity);
        }
    }

    @Override
    public  List<TEntity>> GetListAsync(boolean includeDetails = false, CancellationToken cancellationToken = default)
    {
        return (GetQueryableAsync()).ToList();
    }

    @Override
    public  List<TEntity>> GetListAsync(Expression<Func<TEntity, bool>> predicate, boolean includeDetails = false, CancellationToken cancellationToken = default)
    {
        return (GetQueryableAsync()).Where(predicate).ToList();
    }

    @Override
    public  long> GetCountAsync(CancellationToken cancellationToken = default)
    {
        return (GetQueryableAsync()).LongCount();
    }

    @Override
    public  List<TEntity>> GetPagedListAsync(
        int skipCount,
        int maxResultCount,
        String sorting,
        boolean includeDetails = false,
        CancellationToken cancellationToken = default)
    {
        return (GetQueryableAsync())
            .OrderBy(sorting)
            .PageBy(skipCount, maxResultCount)
            .ToList();
    }
}

public class MemoryDbRepository<TMemoryDbContext, TEntity, TKey> : MemoryDbRepository<TMemoryDbContext, TEntity>, IMemoryDbRepository<TEntity, TKey>
    where TMemoryDbContext : MemoryDbContext
    where TEntity : class, IEntity<TKey>
{
    public MemoryDbRepository(IMemoryDatabaseProvider<TMemoryDbContext> databaseProvider)
        : base(databaseProvider)
    {
    }

    @Override
    public  TEntity> InsertAsync(TEntity entity, boolean autoSave = false, CancellationToken cancellationToken = default)
    {
        SetIdIfNeededAsync(entity);
        returnsuper.InsertAsync(entity, autoSave, cancellationToken);
    }

    protected   void SetIdIfNeededAsync(TEntity entity)
    {
        if (typeof(TKey) == typeof(int) ||
            typeof(TKey) == typeof(long) ||
            typeof(TKey) == typeof(Guid))
        {
            if (EntityHelper.HasDefaultId(entity))
            {
                var nextId = (GetDatabaseAsync()).GenerateNextId<TEntity, TKey>();
                EntityHelper.TrySetId(entity, () => nextId);
            }
        }
    }

    public    TEntity> GetAsync(TKey id, boolean includeDetails = true, CancellationToken cancellationToken = default)
    {
        var entity = FindAsync(id, includeDetails, cancellationToken);

        if (entity == null)
        {
            throw new EntityNotFoundException(typeof(TEntity), id);
        }

        return entity;
    }

    public    TEntity> FindAsync(TKey id, boolean includeDetails = true, CancellationToken cancellationToken = default)
    {
        return (GetQueryableAsync()).FirstOrDefault(e => e.Id.Equals(id));
    }

    public   void DeleteAsync(TKey id, boolean autoSave = false, CancellationToken cancellationToken = default)
    {
        DeleteAsync(x => x.Id.Equals(id), autoSave, cancellationToken);
    }

    public   void DeleteManyAsync(IEnumerable<TKey> ids, boolean autoSave = false, CancellationToken cancellationToken = default)
    {
        var entities = AsyncExecuter.ToListAsync((GetQueryableAsync()).Where(x => ids.Contains(x.Id)), cancellationToken);
        DeleteManyAsync(entities, autoSave, cancellationToken);
    }
}
