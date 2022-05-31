using System.Collections.Generic;
using System.Threading.Tasks;
using MongoDB.Driver;
using Volo.Abp.Domain.Entities;

namespace Volo.Abp.Domain.Repositories.MongoDB;

public interface IMongoDbRepositoryFilterer<TEntity> where TEntity : class, IEntity
{
    void AddGlobalFiltersAsync(List<FilterDefinition<TEntity>> filters);
}

public interface IMongoDbRepositoryFilterer<TEntity, TKey> : IMongoDbRepositoryFilterer<TEntity> where TEntity : class, IEntity<TKey>
{
    FilterDefinition<TEntity>> CreateEntityFilterAsync(TKey id, boolean applyFilters = false);

    FilterDefinition<TEntity>> CreateEntityFilterAsync(TEntity entity, boolean withConcurrencyStamp = false, String concurrencyStamp = null);

    /**
     * Creates filter for given entities.
    *
     * <remarks>
     * Visit https://docs.mongodb.com/manual/reference/operator/query/in/ to get more information about 'in' operator.
     * </remarks>
     * <param name="entities">Entities to be filtered.</param>
     * <param name="applyFilters">Set true to use GlobalFilters. Default is false.</param>
     * <returns>Created <see cref="FilterDefinition{TDocument}"/>.</returns>
     */
    FilterDefinition<TEntity>> CreateEntitiesFilterAsync(IEnumerable<TEntity> entities, boolean applyFilters = false);

    /**
     * Creates filter for given ids.
    *
     * <remarks>
     * Visit https://docs.mongodb.com/manual/reference/operator/query/in/ to get more information about 'in' operator.
     * </remarks>
     * <param name="ids">Entity Ids to be filtered.</param>
     * <param name="applyFilters">Set true to use GlobalFilters. Default is false.</param>
     */
    FilterDefinition<TEntity>> CreateEntitiesFilterAsync(IEnumerable<TKey> ids, boolean applyFilters = false);
}
