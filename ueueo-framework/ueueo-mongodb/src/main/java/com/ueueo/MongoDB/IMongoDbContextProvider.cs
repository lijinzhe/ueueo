using System;
using System.Threading;
using System.Threading.Tasks;

namespace Volo.Abp.MongoDB;

public interface IMongoDbContextProvider<TMongoDbContext>
    where TMongoDbContext : IAbpMongoDbContext
{
    [Obsolete("Use CreateDbContextAsync")]
    TMongoDbContext GetDbContext();

    TMongoDbContext> GetDbContextAsync(CancellationToken cancellationToken = default);
}
