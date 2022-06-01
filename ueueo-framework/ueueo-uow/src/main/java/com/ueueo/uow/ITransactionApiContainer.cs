using System;
using JetBrains.Annotations;

namespace Volo.Abp.Uow;

public interface ITransactionApiContainer
{
    [CanBeNull]
    ITransactionApi FindTransactionApi(@NonNull String key);

    void AddTransactionApi(@NonNull String key, @NonNull ITransactionApi api);

    [NotNull]
    ITransactionApi GetOrAddTransactionApi(@NonNull String key, @NonNull Func<ITransactionApi> factory);
}
