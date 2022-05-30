using JetBrains.Annotations;
using MongoDB.Driver;
using MongoDB.Driver.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Dynamic.Core;
using System.Linq.Expressions;
using System.Threading;
using System.Threading.Tasks;
using Volo.Abp.Auditing;
using Volo.Abp.Data;
using Volo.Abp.Domain.Entities;
using Volo.Abp.Domain.Entities.Events;
using Volo.Abp.EventBus.Distributed;
using Volo.Abp.EventBus.Local;
using Volo.Abp.Guids;
using Volo.Abp.MongoDB;
using Volo.Abp.MultiTenancy;
using Volo.Abp.Uow;

namespace Volo.Abp.Domain.Repositories.MongoDB;

public class MongoDbRepository<TMongoDbContext, TEntity>
    : RepositoryBase<TEntity>,
    IMongoDbRepository<TEntity>
    where TMongoDbContext : IAbpMongoDbContext
    where TEntity : class, IEntity
{
    [Obsolete("Use GetCollectionAsync method.")]
    public   IMongoCollection<TEntity> Collection => DbContext.Collection<TEntity>();

    public  Task<IMongoCollection<TEntity>> GetCollectionAsync(CancellationToken cancellationToken = default)
    {
        return (GetDbContextAsync(GetCancellationToken(cancellationToken))).Collection<TEntity>();
    }

    [Obsolete("Use GetDatabaseAsync method.")]
    public   IMongoDatabase Database => DbContext.Database;

    public  Task<IMongoDatabase> GetDatabaseAsync(CancellationToken cancellationToken = default)
    {
        return (GetDbContextAsync(GetCancellationToken(cancellationToken))).Database;
    }

    [Obsolete("Use GetSessionHandleAsync method.")]
    protected   IClientSessionHandle SessionHandle => DbContext.SessionHandle;

    protected  Task<IClientSessionHandle> GetSessionHandleAsync(CancellationToken cancellationToken = default)
    {
        return (GetDbContextAsync(GetCancellationToken(cancellationToken))).SessionHandle;
    }

    [Obsolete("Use GetDbContextAsync method.")]
    protected   TMongoDbContext DbContext => GetDbContext();

    [Obsolete("Use GetDbContextAsync method.")]
    private TMongoDbContext GetDbContext()
    {
        // Multi-tenancy unaware entities should always use the host connection string
        if (!EntityHelper.IsMultiTenant<TEntity>())
        {
            using (CurrentTenant.Change(null))
            {
                return DbContextProvider.GetDbContext();
            }
        }

        return DbContextProvider.GetDbContext();
    }

    protected Task<TMongoDbContext> GetDbContextAsync(CancellationToken cancellationToken = default)
    {
        cancellationToken = GetCancellationToken(cancellationToken);

        // Multi-tenancy unaware entities should always use the host connection string
        if (!EntityHelper.IsMultiTenant<TEntity>())
        {
            using (CurrentTenant.Change(null))
            {
                return DbContextProvider.GetDbContextAsync(cancellationToken);
            }
        }

        return DbContextProvider.GetDbContextAsync(cancellationToken);
    }

    protected IMongoDbContextProvider<TMongoDbContext> DbContextProvider;//  { get; }

    public ILocalEventBus LocalEventBus => LazyServiceProvider.LazyGetService<ILocalEventBus>(NullLocalEventBus.Instance);

    public IDistributedEventBus DistributedEventBus => LazyServiceProvider.LazyGetService<IDistributedEventBus>(NullDistributedEventBus.Instance);

    public IEntityChangeEventHelper EntityChangeEventHelper => LazyServiceProvider.LazyGetService<IEntityChangeEventHelper>(NullEntityChangeEventHelper.Instance);

    public IGuidGenerator GuidGenerator => LazyServiceProvider.LazyGetService<IGuidGenerator>(SimpleGuidGenerator.Instance);

    public IAuditPropertySetter AuditPropertySetter => LazyServiceProvider.LazyGetRequiredService<IAuditPropertySetter>();

    public IMongoDbBulkOperationProvider BulkOperationProvider => LazyServiceProvider.LazyGetService<IMongoDbBulkOperationProvider>();

    public MongoDbRepository(IMongoDbContextProvider<TMongoDbContext> dbContextProvider)
    {
        DbContextProvider = dbContextProvider;
    }

    @Override
    public Task<TEntity> InsertAsync(
        TEntity entity,
        boolean autoSave = false,
        CancellationToken cancellationToken = default)
    {
        cancellationToken = GetCancellationToken(cancellationToken);

        ApplyAbpConceptsForAddedEntityAsync(entity);

        var dbContext = GetDbContextAsync(cancellationToken);
        var collection = dbContext.Collection<TEntity>();

        if (dbContext.SessionHandle != null)
        {
            collection.InsertOneAsync(
                dbContext.SessionHandle,
                entity,
                cancellationToken: cancellationToken
            );
        }
        else
        {
            collection.InsertOneAsync(
                entity,
                cancellationToken: cancellationToken
            );
        }

        return entity;
    }

    @Override
    public void InsertManyAsync(IEnumerable<TEntity> entities, boolean autoSave = false, CancellationToken cancellationToken = default)
    {
        cancellationToken = GetCancellationToken(cancellationToken);

        var entityArray = entities.ToArray();

        for (var entity in entityArray)
        {
            ApplyAbpConceptsForAddedEntityAsync(entity);
        }

        var dbContext = GetDbContextAsync(cancellationToken);
        var collection = dbContext.Collection<TEntity>();

        if (BulkOperationProvider != null)
        {
            BulkOperationProvider.InsertManyAsync(this, entityArray, dbContext.SessionHandle, autoSave, cancellationToken);
            return;
        }

        if (dbContext.SessionHandle != null)
        {
            collection.InsertManyAsync(
                dbContext.SessionHandle,
                entityArray,
                cancellationToken: cancellationToken);
        }
        else
        {
            collection.InsertManyAsync(
                entityArray,
                cancellationToken: cancellationToken);
        }
    }

    @Override
    public Task<TEntity> UpdateAsync(
        TEntity entity,
        boolean autoSave = false,
        CancellationToken cancellationToken = default)
    {
        cancellationToken = GetCancellationToken(cancellationToken);

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

        var oldConcurrencyStamp = SetNewConcurrencyStamp(entity);
        ReplaceOneResult result;

        var dbContext = GetDbContextAsync(cancellationToken);
        var collection = dbContext.Collection<TEntity>();

        if (dbContext.SessionHandle != null)
        {
            result = collection.ReplaceOneAsync(
                dbContext.SessionHandle,
                CreateEntityFilterAsync(entity, true, oldConcurrencyStamp),
                entity,
                cancellationToken: cancellationToken
            );
        }
        else
        {
            result = collection.ReplaceOneAsync(
                CreateEntityFilterAsync(entity, true, oldConcurrencyStamp),
                entity,
                cancellationToken: cancellationToken
            );
        }

        if (result.MatchedCount <= 0)
        {
            ThrowOptimisticConcurrencyException();
        }

        return entity;
    }

    @Override
    public void UpdateManyAsync(IEnumerable<TEntity> entities, boolean autoSave = false, CancellationToken cancellationToken = default)
    {
        var entityArray = entities.ToArray();

        for (var entity in entityArray)
        {
            SetModificationAuditProperties(entity);

            var isSoftDeleteEntity = typeof(ISoftDelete).IsAssignableFrom(typeof(TEntity));
            if (isSoftDeleteEntity)
            {
                SetDeletionAuditProperties(entity);
                TriggerEntityDeleteEvents(entity);
            }
            else
            {
                TriggerEntityUpdateEvents(entity);
            }

            TriggerDomainEvents(entity);

            SetNewConcurrencyStamp(entity);
        }

        cancellationToken = GetCancellationToken(cancellationToken);
        var dbContext = GetDbContextAsync(cancellationToken);

        if (BulkOperationProvider != null)
        {
            BulkOperationProvider.UpdateManyAsync(this, entityArray, dbContext.SessionHandle, autoSave, cancellationToken);
            return;
        }

        BulkWriteResult result;

        var replaceRequests = new List<WriteModel<TEntity>>();
        for (var entity in entityArray)
        {
            replaceRequests.Add(new ReplaceOneModel<TEntity>(CreateEntityFilterAsync(entity), entity));
        }

        var collection = dbContext.Collection<TEntity>();
        if (dbContext.SessionHandle != null)
        {
            result = collection.BulkWriteAsync(dbContext.SessionHandle, replaceRequests, cancellationToken: cancellationToken);
        }
        else
        {
            result = collection.BulkWriteAsync(replaceRequests, cancellationToken: cancellationToken);
        }

        if (result.MatchedCount < entityArray.Length)
        {
            ThrowOptimisticConcurrencyException();
        }
    }

    @Override
    public void DeleteAsync(
        TEntity entity,
        boolean autoSave = false,
        CancellationToken cancellationToken = default)
    {
        cancellationToken = GetCancellationToken(cancellationToken);

        var dbContext = GetDbContextAsync(cancellationToken);
        var collection = dbContext.Collection<TEntity>();

        var oldConcurrencyStamp = SetNewConcurrencyStamp(entity);

        if (typeof(ISoftDelete).IsAssignableFrom(typeof(TEntity)) && !IsHardDeleted(entity))
        {
            ((ISoftDelete)entity).IsDeleted = true;
            ApplyAbpConceptsForDeletedEntity(entity);

            ReplaceOneResult result;

            if (dbContext.SessionHandle != null)
            {
                result = collection.ReplaceOneAsync(
                    dbContext.SessionHandle,
                    CreateEntityFilterAsync(entity, true, oldConcurrencyStamp),
                    entity,
                    cancellationToken: cancellationToken
                );
            }
            else
            {
                result = collection.ReplaceOneAsync(
                    CreateEntityFilterAsync(entity, true, oldConcurrencyStamp),
                    entity,
                    cancellationToken: cancellationToken
                );
            }

            if (result.MatchedCount <= 0)
            {
                ThrowOptimisticConcurrencyException();
            }
        }
        else
        {
            ApplyAbpConceptsForDeletedEntity(entity);

            DeleteResult result;

            if (dbContext.SessionHandle != null)
            {
                result = collection.DeleteOneAsync(
                    dbContext.SessionHandle,
                    CreateEntityFilterAsync(entity, true, oldConcurrencyStamp),
                    cancellationToken: cancellationToken
                );
            }
            else
            {
                result = collection.DeleteOneAsync(
                    CreateEntityFilterAsync(entity, true, oldConcurrencyStamp),
                    cancellationToken
                );
            }

            if (result.DeletedCount <= 0)
            {
                ThrowOptimisticConcurrencyException();
            }
        }
    }

    @Override
    public void DeleteManyAsync(
       IEnumerable<TEntity> entities,
       boolean autoSave = false,
       CancellationToken cancellationToken = default)
    {
        cancellationToken = GetCancellationToken(cancellationToken);

        var softDeletedEntities = new Dictionary<TEntity, String>();
        var hardDeletedEntities = new List<TEntity>();

        for (var entity in entities)
        {
            if (typeof(ISoftDelete).IsAssignableFrom(typeof(TEntity)) && !IsHardDeleted(entity))
            {
                ((ISoftDelete)entity).IsDeleted = true;

                softDeletedEntities.Add(entity, SetNewConcurrencyStamp(entity));
            }
            else
            {
                hardDeletedEntities.Add(entity);
            }

            ApplyAbpConceptsForDeletedEntity(entity);
        }

        var dbContext = GetDbContextAsync(cancellationToken);
        var collection = dbContext.Collection<TEntity>();

        if (BulkOperationProvider != null)
        {
            BulkOperationProvider.DeleteManyAsync(this, entities, dbContext.SessionHandle, autoSave, cancellationToken);
            return;
        }

        if (softDeletedEntities.Count > 0)
        {
            BulkWriteResult updateResult;

            var replaceRequests = new List<WriteModel<TEntity>>();
            for (var softDeletedEntity in softDeletedEntities)
            {
                replaceRequests.Add(new ReplaceOneModel<TEntity>(CreateEntityFilterAsync(softDeletedEntity.Key, true, softDeletedEntity.Value), softDeletedEntity.Key));
            }

            if (dbContext.SessionHandle != null)
            {
                updateResult = collection.BulkWriteAsync(dbContext.SessionHandle, replaceRequests, cancellationToken: cancellationToken);
            }
            else
            {
                updateResult = collection.BulkWriteAsync(replaceRequests, cancellationToken: cancellationToken);
            }

            if (updateResult.MatchedCount < softDeletedEntities.Count)
            {
                ThrowOptimisticConcurrencyException();
            }
        }

        if (hardDeletedEntities.Count > 0)
        {
            DeleteResult deleteResult;
            var hardDeletedEntitiesCount = hardDeletedEntities.Count;

            if (dbContext.SessionHandle != null)
            {
                deleteResult = collection.DeleteManyAsync(
                    dbContext.SessionHandle,
                    CreateEntitiesFilterAsync(hardDeletedEntities),
                    cancellationToken: cancellationToken);
            }
            else
            {
                deleteResult = collection.DeleteManyAsync(
                    CreateEntitiesFilterAsync(hardDeletedEntities),
                    cancellationToken: cancellationToken);
            }

            if (deleteResult.DeletedCount < hardDeletedEntitiesCount)
            {
                ThrowOptimisticConcurrencyException();
            }
        }
    }

    @Override
    public Task<List<TEntity>> GetListAsync(boolean includeDetails = false, CancellationToken cancellationToken = default)
    {
        cancellationToken = GetCancellationToken(cancellationToken);
        return (GetMongoQueryableAsync(cancellationToken)).ToListAsync(cancellationToken);
    }

    @Override
    public Task<List<TEntity>> GetListAsync(Expression<Func<TEntity, bool>> predicate, boolean includeDetails = false, CancellationToken cancellationToken = default)
    {
        cancellationToken = GetCancellationToken(cancellationToken);
        return (GetMongoQueryableAsync(cancellationToken)).Where(predicate).ToListAsync(cancellationToken);
    }

    @Override
    public Task<long> GetCountAsync(CancellationToken cancellationToken = default)
    {
        cancellationToken = GetCancellationToken(cancellationToken);
        return (GetMongoQueryableAsync(cancellationToken)).LongCountAsync(cancellationToken);
    }

    @Override
    public Task<List<TEntity>> GetPagedListAsync(
        int skipCount,
        int maxResultCount,
        String sorting,
        boolean includeDetails = false,
        CancellationToken cancellationToken = default)
    {
        cancellationToken = GetCancellationToken(cancellationToken);

        return (GetMongoQueryableAsync(cancellationToken))
            .OrderBy(sorting)
            .As<IMongoQueryable<TEntity>>()
            .PageBy<TEntity, IMongoQueryable<TEntity>>(skipCount, maxResultCount)
            .ToListAsync(cancellationToken);
    }

    @Override
    public void DeleteAsync(
        Expression<Func<TEntity, bool>> predicate,
        boolean autoSave = false,
        CancellationToken cancellationToken = default)
    {
        cancellationToken = GetCancellationToken(cancellationToken);

        var entities = (GetMongoQueryableAsync(cancellationToken))
            .Where(predicate)
            .ToListAsync(cancellationToken);

        DeleteManyAsync(entities, autoSave, cancellationToken);
    }

    [Obsolete("Use GetQueryableAsync method.")]
    protected override IQueryable<TEntity> GetQueryable()
    {
        return GetMongoQueryable();
    }

    @Override
    public Task<IQueryable<TEntity>> GetQueryableAsync()
    {
        return GetMongoQueryableAsync();
    }

    @Override
    public Task<TEntity> FindAsync(
        Expression<Func<TEntity, bool>> predicate,
        boolean includeDetails = true,
        CancellationToken cancellationToken = default)
    {
        cancellationToken = GetCancellationToken(cancellationToken);

        return (GetMongoQueryableAsync(cancellationToken))
            .Where(predicate)
            .SingleOrDefaultAsync(cancellationToken);
    }

    [Obsolete("Use GetMongoQueryableAsync method.")]
    public   IMongoQueryable<TEntity> GetMongoQueryable()
    {
        return ApplyDataFilters(
            SessionHandle != null
                ? Collection.AsQueryable(SessionHandle)
                : Collection.AsQueryable()
        );
    }

    public   Task<IMongoQueryable<TEntity>> GetMongoQueryableAsync(CancellationToken cancellationToken = default)
    {
        return GetMongoQueryableAsync<TEntity>(cancellationToken);
    }

    protected    Task<IMongoQueryable<TOtherEntity>> GetMongoQueryableAsync<TOtherEntity>(CancellationToken cancellationToken = default)
    {
        cancellationToken = GetCancellationToken(cancellationToken);

        var dbContext = GetDbContextAsync(cancellationToken);
        var collection = dbContext.Collection<TOtherEntity>();

        return ApplyDataFilters<IMongoQueryable<TOtherEntity>, TOtherEntity>(
            dbContext.SessionHandle != null
                ? collection.AsQueryable(dbContext.SessionHandle)
                : collection.AsQueryable()
        );
    }

    public    Task<IAggregateFluent<TEntity>> GetAggregateAsync(CancellationToken cancellationToken = default)
    {
        cancellationToken = GetCancellationToken(cancellationToken);

        var dbContext = GetDbContextAsync(cancellationToken);
        var collection = GetCollectionAsync(cancellationToken);

        var aggregate = dbContext.SessionHandle != null
                ? collection.Aggregate(dbContext.SessionHandle)
                : collection.Aggregate();

        if (typeof(ISoftDelete).IsAssignableFrom(typeof(TEntity)) && DataFilter.IsEnabled<ISoftDelete>())
        {
            aggregate = aggregate.Match(e => ((ISoftDelete)e).IsDeleted == false);
        }

        if (typeof(IMultiTenant).IsAssignableFrom(typeof(TEntity)) && DataFilter.IsEnabled<IMultiTenant>())
        {
            var tenantId = CurrentTenant.Id;
            aggregate = aggregate.Match(e => ((IMultiTenant)e).TenantId == tenantId);
        }

        return aggregate;
    }

    protected   boolean IsHardDeleted(TEntity entity)
    {
        var hardDeletedEntities = UnitOfWorkManager?.Current?.Items.GetOrDefault(UnitOfWorkItemNames.HardDeletedEntities) as HashSet<IEntity>;
        if (hardDeletedEntities == null)
        {
            return false;
        }

        return hardDeletedEntities.Contains(entity);
    }

    protected   Task<FilterDefinition<TEntity>> CreateEntityFilterAsync(TEntity entity, boolean withConcurrencyStamp = false, String concurrencyStamp = null)
    {
        throw new NotImplementedException(
            $"{nameof(CreateEntityFilterAsync)} is not implemented for MongoDB by default. It should be overriden and implemented by the deriving class!"
        );
    }

    protected   Task<FilterDefinition<TEntity>> CreateEntitiesFilterAsync(IEnumerable<TEntity> entities, boolean withConcurrencyStamp = false)
    {
        throw new NotImplementedException(
          $"{nameof(CreateEntitiesFilterAsync)} is not implemented for MongoDB by default. It should be overriden and implemented by the deriving class!"
      );
    }

    protected void ApplyAbpConceptsForAddedEntityAsync(TEntity entity)
    {
        CheckAndSetId(entity);
        SetCreationAuditProperties(entity);
        TriggerEntityCreateEvents(entity);
        TriggerDomainEvents(entity);
        return Task.CompletedTask;
    }

    private void TriggerEntityCreateEvents(TEntity entity)
    {
        EntityChangeEventHelper.PublishEntityCreatingEvent(entity);
        EntityChangeEventHelper.PublishEntityCreatedEvent(entity);
    }

    protected   void TriggerEntityUpdateEvents(TEntity entity)
    {
        EntityChangeEventHelper.PublishEntityUpdatingEvent(entity);
        EntityChangeEventHelper.PublishEntityUpdatedEvent(entity);
    }

    protected   void ApplyAbpConceptsForDeletedEntity(TEntity entity)
    {
        SetDeletionAuditProperties(entity);
        TriggerEntityDeleteEvents(entity);
        TriggerDomainEvents(entity);
    }

    protected   void TriggerEntityDeleteEvents(TEntity entity)
    {
        EntityChangeEventHelper.PublishEntityDeletingEvent(entity);
        EntityChangeEventHelper.PublishEntityDeletedEvent(entity);
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
                        distributedEvent.EventOrder
                    )
                );
            }

            generatesDomainEventsEntity.ClearDistributedEvents();
        }
    }

    /**
     * Sets a new <see cref="IHasConcurrencyStamp.ConcurrencyStamp"/> value
     * if given entity implements <see cref="IHasConcurrencyStamp"/> interface.
     * Returns the old <see cref="IHasConcurrencyStamp.ConcurrencyStamp"/> value.
    */
    protected   String SetNewConcurrencyStamp(TEntity entity)
    {
        if (!(entity is IHasConcurrencyStamp concurrencyStampEntity))
        {
            return null;
        }

        var oldConcurrencyStamp = concurrencyStampEntity.ConcurrencyStamp;
        concurrencyStampEntity.ConcurrencyStamp = Guid.NewGuid().ToString("N");
        return oldConcurrencyStamp;
    }

    protected   void ThrowOptimisticConcurrencyException()
    {
        throw new AbpDbConcurrencyException("Database operation expected to affect 1 row but actually affected 0 row. Data may have been modified or deleted since entities were loaded. This exception has been thrown on optimistic concurrency check.");
    }
}

public class MongoDbRepository<TMongoDbContext, TEntity, TKey>
    : MongoDbRepository<TMongoDbContext, TEntity>,
    IMongoDbRepository<TEntity, TKey>
    where TMongoDbContext : IAbpMongoDbContext
    where TEntity : class, IEntity<TKey>
{
    public IMongoDbRepositoryFilterer<TEntity, TKey> RepositoryFilterer;// { get; set; }

    public MongoDbRepository(IMongoDbContextProvider<TMongoDbContext> dbContextProvider)
        : base(dbContextProvider)
    {

    }

    public    Task<TEntity> GetAsync(
        TKey id,
        boolean includeDetails = true,
        CancellationToken cancellationToken = default)
    {
        var entity = FindAsync(id, includeDetails, GetCancellationToken(cancellationToken));

        if (entity == null)
        {
            throw new EntityNotFoundException(typeof(TEntity), id);
        }

        return entity;
    }

    public    Task<TEntity> FindAsync(
        TKey id,
        boolean includeDetails = true,
        CancellationToken cancellationToken = default)
    {
        cancellationToken = GetCancellationToken(cancellationToken);

        return ApplyDataFilters(GetMongoQueryableAsync(cancellationToken)).Where(x => x.Id.Equals(id)).FirstOrDefaultAsync(cancellationToken);
    }

    public void DeleteAsync(
        TKey id,
        boolean autoSave = false,
        CancellationToken cancellationToken = default)
    {
        return DeleteAsync(x => x.Id.Equals(id), autoSave, cancellationToken);
    }

    public   void DeleteManyAsync(@Nonnull IEnumerable<TKey> ids, boolean autoSave = false, CancellationToken cancellationToken = default)
    {
        cancellationToken = GetCancellationToken(cancellationToken);

        var entities = (GetMongoQueryableAsync(cancellationToken))
            .Where(x => ids.Contains(x.Id))
            .ToListAsync(cancellationToken);

        DeleteManyAsync(entities, autoSave, cancellationToken);
    }

    protected  override Task<FilterDefinition<TEntity>> CreateEntityFilterAsync(TEntity entity, boolean withConcurrencyStamp = false, String concurrencyStamp = null)
    {
        return RepositoryFilterer.CreateEntityFilterAsync(entity, withConcurrencyStamp, concurrencyStamp);
    }

    protected  override Task<FilterDefinition<TEntity>> CreateEntitiesFilterAsync(IEnumerable<TEntity> entities, boolean withConcurrencyStamp = false)
    {
        return RepositoryFilterer.CreateEntitiesFilterAsync(entities, withConcurrencyStamp);
    }
}
