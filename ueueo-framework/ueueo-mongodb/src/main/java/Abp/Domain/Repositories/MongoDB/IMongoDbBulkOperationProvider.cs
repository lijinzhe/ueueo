using MongoDB.Driver;
using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using Volo.Abp.Domain.Entities;

namespace Volo.Abp.Domain.Repositories.MongoDB;

public interface IMongoDbBulkOperationProvider
{
    void InsertManyAsync<TEntity>(
       IMongoDbRepository<TEntity> repository,
       IEnumerable<TEntity> entities,
        IClientSessionHandle sessionHandle,
       boolean autoSave,
       CancellationToken cancellationToken
   )
       where TEntity : class, IEntity;

    void UpdateManyAsync<TEntity>(
        IMongoDbRepository<TEntity> repository,
        IEnumerable<TEntity> entities,
        IClientSessionHandle sessionHandle,
        boolean autoSave,
        CancellationToken cancellationToken
    )
        where TEntity : class, IEntity;

    void DeleteManyAsync<TEntity>(
        IMongoDbRepository<TEntity> repository,
        IEnumerable<TEntity> entities,
        IClientSessionHandle sessionHandle,
        boolean autoSave,
        CancellationToken cancellationToken
    )
        where TEntity : class, IEntity;
}
