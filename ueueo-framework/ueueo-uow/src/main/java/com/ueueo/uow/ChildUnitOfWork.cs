using System;
using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using JetBrains.Annotations;

namespace Volo.Abp.Uow;

internal class ChildUnitOfWork : IUnitOfWork
{
    public ID Id => _parent.Id;

    public IAbpUnitOfWorkOptions Options => _parent.Options;

    public IUnitOfWork Outer => _parent.Outer;

    public boolean IsReserved => _parent.IsReserved;

    public boolean IsDisposed => _parent.IsDisposed;

    public boolean IsCompleted => _parent.IsCompleted;

    public String ReservationName => _parent.ReservationName;

    public event EventHandler<UnitOfWorkFailedEventArgs> Failed;
    public event EventHandler<UnitOfWorkEventArgs> Disposed;

    public IServiceProvider ServiceProvider => _parent.ServiceProvider;

    public Dictionary<String, Object> Items => _parent.Items;

    private readonly IUnitOfWork _parent;

    public ChildUnitOfWork(@NonNull IUnitOfWork parent)
    {
        Objects.requireNonNull(parent, nameof(parent));

        _parent = parent;

        _parent.Failed += (sender, args) => { Failed.InvokeSafely(sender, args); };
        _parent.Disposed += (sender, args) => { Disposed.InvokeSafely(sender, args); };
    }

    public void SetOuter(IUnitOfWork outer)
    {
        _parent.SetOuter(outer);
    }

    public void Initialize(AbpUnitOfWorkOptions options)
    {
        _parent.Initialize(options);
    }

    public void Reserve(String reservationName)
    {
        _parent.Reserve(reservationName);
    }

    public void SaveChangesAsync(CancellationToken cancellationToken = default)
    {
        return _parent.SaveChangesAsync(cancellationToken);
    }

    public void CompleteAsync(CancellationToken cancellationToken = default)
    {
        return Task.CompletedTask;
    }

    public void RollbackAsync(CancellationToken cancellationToken = default)
    {
        return _parent.RollbackAsync(cancellationToken);
    }

    public void OnCompleted(Func<Task> handler)
    {
        _parent.OnCompleted(handler);
    }

    public void AddOrReplaceLocalEvent(
        UnitOfWorkEventRecord eventRecord,
        Predicate<UnitOfWorkEventRecord> replacementSelector = null)
    {
        _parent.AddOrReplaceLocalEvent(eventRecord, replacementSelector);
    }

    public void AddOrReplaceDistributedEvent(
        UnitOfWorkEventRecord eventRecord,
        Predicate<UnitOfWorkEventRecord> replacementSelector = null)
    {
        _parent.AddOrReplaceDistributedEvent(eventRecord, replacementSelector);
    }

    public IDatabaseApi FindDatabaseApi(String key)
    {
        return _parent.FindDatabaseApi(key);
    }

    public void AddDatabaseApi(String key, IDatabaseApi api)
    {
        _parent.AddDatabaseApi(key, api);
    }

    public IDatabaseApi GetOrAddDatabaseApi(String key, Func<IDatabaseApi> factory)
    {
        return _parent.GetOrAddDatabaseApi(key, factory);
    }

    public ITransactionApi FindTransactionApi(String key)
    {
        return _parent.FindTransactionApi(key);
    }

    public void AddTransactionApi(String key, ITransactionApi api)
    {
        _parent.AddTransactionApi(key, api);
    }

    public ITransactionApi GetOrAddTransactionApi(String key, Func<ITransactionApi> factory)
    {
        return _parent.GetOrAddTransactionApi(key, factory);
    }

    public void Dispose()
    {

    }

    @Override public String toString()
    {
        return $"[UnitOfWork {Id}]";
    }
}
