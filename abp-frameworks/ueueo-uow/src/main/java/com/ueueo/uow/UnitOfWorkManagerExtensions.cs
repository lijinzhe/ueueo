using System;
using System.Data;
using JetBrains.Annotations;

namespace Volo.Abp.Uow;

public static class UnitOfWorkManagerExtensions
{
    [NotNull]
    public static IUnitOfWork Begin(
        @NonNull this IUnitOfWorkManager unitOfWorkManager,
        boolean requiresNew = false,
        boolean isTransactional = false,
        IsolationLevel? isolationLevel = null,
        int? timeout = null)
    {
        Objects.requireNonNull(unitOfWorkManager, nameof(unitOfWorkManager));

        return unitOfWorkManager.Begin(new AbpUnitOfWorkOptions
        {
            IsTransactional = isTransactional,
            IsolationLevel = isolationLevel,
            Timeout = timeout
        }, requiresNew);
    }

    public static void BeginReserved(@NonNull this IUnitOfWorkManager unitOfWorkManager, @NonNull String reservationName)
    {
        Objects.requireNonNull(unitOfWorkManager, nameof(unitOfWorkManager));
        Objects.requireNonNull(reservationName, nameof(reservationName));

        unitOfWorkManager.BeginReserved(reservationName, new AbpUnitOfWorkOptions());
    }

    public static void TryBeginReserved(@NonNull this IUnitOfWorkManager unitOfWorkManager, @NonNull String reservationName)
    {
        Objects.requireNonNull(unitOfWorkManager, nameof(unitOfWorkManager));
        Objects.requireNonNull(reservationName, nameof(reservationName));

        unitOfWorkManager.TryBeginReserved(reservationName, new AbpUnitOfWorkOptions());
    }
}
