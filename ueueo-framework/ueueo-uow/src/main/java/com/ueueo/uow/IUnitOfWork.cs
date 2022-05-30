using System;
using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using JetBrains.Annotations;

namespace Volo.Abp.Uow;

public interface IUnitOfWork : IDatabaseApiContainer, ITransactionApiContainer, IDisposable
{
    ID Id;//  { get; }

    Dictionary<String, Object> Items;//  { get; }

    //TODO: Switch to OnFailed (sync) and OnDisposed (sync) methods to be compatible with OnCompleted
    event EventHandler<UnitOfWorkFailedEventArgs> Failed;

    event EventHandler<UnitOfWorkEventArgs> Disposed;

    IAbpUnitOfWorkOptions Options;//  { get; }

    IUnitOfWork Outer;//  { get; }

    boolean IsReserved;//  { get; }

    boolean IsDisposed;//  { get; }

    boolean IsCompleted;//  { get; }

    String ReservationName;//  { get; }

    void SetOuter(@Nullable IUnitOfWork outer);

    void Initialize(@Nonnull AbpUnitOfWorkOptions options);

    void Reserve(@Nonnull String reservationName);

    void SaveChangesAsync(CancellationToken cancellationToken = default);

    void CompleteAsync(CancellationToken cancellationToken = default);

    void RollbackAsync(CancellationToken cancellationToken = default);

    void OnCompleted(Func<Task> handler);

    void AddOrReplaceLocalEvent(
        UnitOfWorkEventRecord eventRecord,
        Predicate<UnitOfWorkEventRecord> replacementSelector = null
    );

    void AddOrReplaceDistributedEvent(
        UnitOfWorkEventRecord eventRecord,
        Predicate<UnitOfWorkEventRecord> replacementSelector = null
    );
}
