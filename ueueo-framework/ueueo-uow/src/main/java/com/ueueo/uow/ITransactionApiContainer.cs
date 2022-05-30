using System;
using JetBrains.Annotations;

namespace Volo.Abp.Uow;

public interface ITransactionApiContainer
{
    [CanBeNull]
    ITransactionApi FindTransactionApi(@Nonnull String key);

    void AddTransactionApi(@Nonnull String key, @Nonnull ITransactionApi api);

    [NotNull]
    ITransactionApi GetOrAddTransactionApi(@Nonnull String key, @Nonnull Func<ITransactionApi> factory);
}
