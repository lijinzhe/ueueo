using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using MongoDB.Driver;
using Volo.Abp.Data;
using Volo.Abp.Domain.Entities;
using Volo.Abp.MultiTenancy;

namespace Volo.Abp.Domain.Repositories.MongoDB;

public class MongoDbRepositoryFilterer<TEntity> : IMongoDbRepositoryFilterer<TEntity>
    where TEntity : class, IEntity
{
    protected IDataFilter DataFilter;//  { get; }

    protected ICurrentTenant CurrentTenant;//  { get; }

    public MongoDbRepositoryFilterer(IDataFilter dataFilter, ICurrentTenant currentTenant)
    {
        DataFilter = dataFilter;
        CurrentTenant = currentTenant;
    }

    public void AddGlobalFiltersAsync(List<FilterDefinition<TEntity>> filters)
    {
        if (typeof(ISoftDelete).IsAssignableFrom(typeof(TEntity)) && DataFilter.IsEnabled<ISoftDelete>())
        {
            filters.Add(Builders<TEntity>.Filter.Eq(e => ((ISoftDelete)e).IsDeleted, false));
        }

        if (typeof(IMultiTenant).IsAssignableFrom(typeof(TEntity)))
        {
            var tenantId = CurrentTenant.Id;
            filters.Add(Builders<TEntity>.Filter.Eq(e => ((IMultiTenant)e).TenantId, tenantId));
        }

        return Task.CompletedTask;
    }
}

public class MongoDbRepositoryFilterer<TEntity, TKey> : MongoDbRepositoryFilterer<TEntity>,
    IMongoDbRepositoryFilterer<TEntity, TKey>
    where TEntity : class, IEntity<TKey>
{
    public MongoDbRepositoryFilterer(IDataFilter dataFilter, ICurrentTenant currentTenant)
        : base(dataFilter, currentTenant)
    {
    }

    public    Task<FilterDefinition<TEntity>> CreateEntityFilterAsync(TKey id, boolean applyFilters = false)
    {
        var filters = new List<FilterDefinition<TEntity>>
            {
                Builders<TEntity>.Filter.Eq(e => e.Id, id)
            };

        if (applyFilters)
        {
            AddGlobalFiltersAsync(filters);
        }

        return Builders<TEntity>.Filter.And(filters);
    }

    public   Task<FilterDefinition<TEntity>> CreateEntityFilterAsync(TEntity entity, boolean withConcurrencyStamp = false, String concurrencyStamp = null)
    {
        if (!withConcurrencyStamp || !(entity is IHasConcurrencyStamp entityWithConcurrencyStamp))
        {
            return Task.FromResult(Builders<TEntity>.Filter.Eq(e => e.Id, entity.Id));
        }

        if (concurrencyStamp == null)
        {
            concurrencyStamp = entityWithConcurrencyStamp.ConcurrencyStamp;
        }

        return Task.FromResult<FilterDefinition<TEntity>>(Builders<TEntity>.Filter.And(
            Builders<TEntity>.Filter.Eq(e => e.Id, entity.Id),
            Builders<TEntity>.Filter.Eq(e => ((IHasConcurrencyStamp)e).ConcurrencyStamp, concurrencyStamp)
        ));
    }

    public    Task<FilterDefinition<TEntity>> CreateEntitiesFilterAsync(IEnumerable<TEntity> entities, boolean applyFilters = false)
    {
        return CreateEntitiesFilterAsync(entities.Select(s => s.Id), applyFilters);
    }

    public    Task<FilterDefinition<TEntity>> CreateEntitiesFilterAsync(IEnumerable<TKey> ids, boolean applyFilters = false)
    {
        var filters = new List<FilterDefinition<TEntity>>()
            {
                Builders<TEntity>.Filter.In(e => e.Id, ids),
            };

        if (applyFilters)
        {
            AddGlobalFiltersAsync(filters);
        }

        return Builders<TEntity>.Filter.And(filters);
    }
}
