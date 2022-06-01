using JetBrains.Annotations;

namespace Volo.Abp.Uow;

public interface IUnitOfWorkManager
{
    [CanBeNull]
    IUnitOfWork Current;//  { get; }

    [NotNull]
    IUnitOfWork Begin(@NonNull AbpUnitOfWorkOptions options, boolean requiresNew = false);

    [NotNull]
    IUnitOfWork Reserve(@NonNull String reservationName, boolean requiresNew = false);

    void BeginReserved(@NonNull String reservationName, @NonNull AbpUnitOfWorkOptions options);

    boolean TryBeginReserved(@NonNull String reservationName, @NonNull AbpUnitOfWorkOptions options);
}
