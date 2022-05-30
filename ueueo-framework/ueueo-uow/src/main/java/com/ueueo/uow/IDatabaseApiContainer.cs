using System;
using JetBrains.Annotations;
using Volo.Abp.DependencyInjection;

namespace Volo.Abp.Uow;

public interface IDatabaseApiContainer : IServiceProviderAccessor
{
    [CanBeNull]
    IDatabaseApi FindDatabaseApi(@Nonnull String key);

    void AddDatabaseApi(@Nonnull String key, @Nonnull IDatabaseApi api);

    [NotNull]
    IDatabaseApi GetOrAddDatabaseApi(@Nonnull String key, @Nonnull Func<IDatabaseApi> factory);
}
