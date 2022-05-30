using JetBrains.Annotations;

namespace Volo.Abp.Uow;

public interface IUnitOfWorkManager
{
    [CanBeNull]
    IUnitOfWork Current;//  { get; }

    [NotNull]
    IUnitOfWork Begin(@Nonnull AbpUnitOfWorkOptions options, boolean requiresNew = false);

    [NotNull]
    IUnitOfWork Reserve(@Nonnull String reservationName, boolean requiresNew = false);

    void BeginReserved(@Nonnull String reservationName, @Nonnull AbpUnitOfWorkOptions options);

    boolean TryBeginReserved(@Nonnull String reservationName, @Nonnull AbpUnitOfWorkOptions options);
}
