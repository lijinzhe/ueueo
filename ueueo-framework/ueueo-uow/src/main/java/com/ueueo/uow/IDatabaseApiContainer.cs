using System;
using JetBrains.Annotations;
using Volo.Abp.DependencyInjection;

namespace Volo.Abp.Uow;

public interface IDatabaseApiContainer : IServiceProviderAccessor
{
    [CanBeNull]
    IDatabaseApi FindDatabaseApi(@NonNull String key);

    void AddDatabaseApi(@NonNull String key, @NonNull IDatabaseApi api);

    [NotNull]
    IDatabaseApi GetOrAddDatabaseApi(@NonNull String key, @NonNull Func<IDatabaseApi> factory);
}
