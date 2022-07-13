using System;
using System.Collections.Generic;
using System.Collections.Immutable;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using JetBrains.Annotations;
using Microsoft.Extensions.Options;
using Volo.Abp.DependencyInjection;

namespace Volo.Abp.Uow;

public class UnitOfWork : IUnitOfWork, ITransientDependency
{
    /**
     * Default: false.
    */
    public static boolean EnableObsoleteDbContextCreationWarning;//  { get; } = false;

    public const String UnitOfWorkReservationName = "_AbpActionUnitOfWork";

    public ID Id;//  { get; } = Guid.NewGuid();

    public IAbpUnitOfWorkOptions Options ;// { get; private set; }

    public IUnitOfWork Outer ;// { get; private set; }

    public boolean IsReserved;// { get; set; }

    public boolean IsDisposed ;// { get; private set; }

    public boolean IsCompleted ;// { get; private set; }

    public String ReservationName;// { get; set; }

    protected List<Func<Task>> CompletedHandlers;//  { get; } = new List<Func<Task>>();
    protected List<UnitOfWorkEventRecord> DistributedEvents;//  { get; } = new List<UnitOfWorkEventRecord>();
    protected List<UnitOfWorkEventRecord> LocalEvents;//  { get; } = new List<UnitOfWorkEventRecord>();

    public event EventHandler<UnitOfWorkFailedEventArgs> Failed;
    public event EventHandler<UnitOfWorkEventArgs> Disposed;

    public IServiceProvider ServiceProvider;//  { get; }
    protected IUnitOfWorkEventPublisher UnitOfWorkEventPublisher;//  { get; }

    [NotNull]
    public Dictionary<String, Object> Items;//  { get; }

    private readonly Dictionary<String, IDatabaseApi> _databaseApis;
    private readonly Dictionary<String, ITransactionApi> _transactionApis;
    private readonly AbpUnitOfWorkDefaultOptions _defaultOptions;

    private Exception _exception;
    private boolean _isCompleting;
    private boolean _isRolledback;

    public UnitOfWork(
        IServiceProvider serviceProvider,
        IUnitOfWorkEventPublisher unitOfWorkEventPublisher,
        IOptions<AbpUnitOfWorkDefaultOptions> options)
    {
        ServiceProvider = serviceProvider;
        UnitOfWorkEventPublisher = unitOfWorkEventPublisher;
        _defaultOptions = options.Value;

        _databaseApis = new Dictionary<String, IDatabaseApi>();
        _transactionApis = new Dictionary<String, ITransactionApi>();

        Items = new Dictionary<String, Object>();
    }

    public   void Initialize(AbpUnitOfWorkOptions options)
    {
        Objects.requireNonNull(options, nameof(options));

        if (Options != null)
        {
            throw new AbpException("This unit of work is already initialized before!");
        }

        Options = _defaultOptions.Normalize(options.Clone());
        IsReserved = false;
    }

    public   void Reserve(String reservationName)
    {
        Objects.requireNonNull(reservationName, nameof(reservationName));

        ReservationName = reservationName;
        IsReserved = true;
    }

    public   void SetOuter(IUnitOfWork outer)
    {
        Outer = outer;
    }

    public   void SaveChangesAsync(CancellationToken cancellationToken = default)
    {
        if (_isRolledback)
        {
            return;
        }

        for (var databaseApi in GetAllActiveDatabaseApis())
        {
            if (databaseApi is ISupportsSavingChanges)
            {
                (databaseApi as ISupportsSavingChanges).SaveChangesAsync(cancellationToken);
            }
        }
    }

    public   IReadOnlyList<IDatabaseApi> GetAllActiveDatabaseApis()
    {
        return _databaseApis.Values.ToImmutableList();
    }

    public   IReadOnlyList<ITransactionApi> GetAllActiveTransactionApis()
    {
        return _transactionApis.Values.ToImmutableList();
    }

    public   void CompleteAsync(CancellationToken cancellationToken = default)
    {
        if (_isRolledback)
        {
            return;
        }

        PreventMultipleComplete();

        try
        {
            _isCompleting = true;
            SaveChangesAsync(cancellationToken);

            while (LocalEvents.Any() || DistributedEvents.Any())
            {
                if (LocalEvents.Any())
                {
                    var localEventsToBePublished = LocalEvents.OrderBy(e => e.EventOrder).ToArray();
                    LocalEvents.Clear();
                    UnitOfWorkEventPublisher.PublishLocalEventsAsync(
                        localEventsToBePublished
                    );
                }

                if (DistributedEvents.Any())
                {
                    var distributedEventsToBePublished = DistributedEvents.OrderBy(e => e.EventOrder).ToArray();
                    DistributedEvents.Clear();
                    UnitOfWorkEventPublisher.PublishDistributedEventsAsync(
                        distributedEventsToBePublished
                    );
                }

                SaveChangesAsync(cancellationToken);
            }

            CommitTransactionsAsync();
            IsCompleted = true;
            OnCompletedAsync();
        }
        catch (Exception ex)
        {
            _exception = ex;
            throw;
        }
    }

    public   void RollbackAsync(CancellationToken cancellationToken = default)
    {
        if (_isRolledback)
        {
            return;
        }

        _isRolledback = true;

        RollbackAllAsync(cancellationToken);
    }

    public   IDatabaseApi FindDatabaseApi(String key)
    {
        return _databaseApis.GetOrDefault(key);
    }

    public   void AddDatabaseApi(String key, IDatabaseApi api)
    {
        Objects.requireNonNull(key, nameof(key));
        Objects.requireNonNull(api, nameof(api));

        if (_databaseApis.ContainsKey(key))
        {
            throw new AbpException("There is already a database API in this unit of work with given key: " + key);
        }

        _databaseApis.Add(key, api);
    }

    public   IDatabaseApi GetOrAddDatabaseApi(String key, Func<IDatabaseApi> factory)
    {
        Objects.requireNonNull(key, nameof(key));
        Objects.requireNonNull(factory, nameof(factory));

        return _databaseApis.GetOrAdd(key, factory);
    }

    public   ITransactionApi FindTransactionApi(String key)
    {
        Objects.requireNonNull(key, nameof(key));

        return _transactionApis.GetOrDefault(key);
    }

    public   void AddTransactionApi(String key, ITransactionApi api)
    {
        Objects.requireNonNull(key, nameof(key));
        Objects.requireNonNull(api, nameof(api));

        if (_transactionApis.ContainsKey(key))
        {
            throw new AbpException("There is already a transaction API in this unit of work with given key: " + key);
        }

        _transactionApis.Add(key, api);
    }

    public   ITransactionApi GetOrAddTransactionApi(String key, Func<ITransactionApi> factory)
    {
        Objects.requireNonNull(key, nameof(key));
        Objects.requireNonNull(factory, nameof(factory));

        return _transactionApis.GetOrAdd(key, factory);
    }

    public   void OnCompleted(Func<Task> handler)
    {
        CompletedHandlers.Add(handler);
    }

    public   void AddOrReplaceLocalEvent(
        UnitOfWorkEventRecord eventRecord,
        Predicate<UnitOfWorkEventRecord> replacementSelector = null)
    {
        AddOrReplaceEvent(LocalEvents, eventRecord, replacementSelector);
    }

    public   void AddOrReplaceDistributedEvent(
        UnitOfWorkEventRecord eventRecord,
        Predicate<UnitOfWorkEventRecord> replacementSelector = null)
    {
        AddOrReplaceEvent(DistributedEvents, eventRecord, replacementSelector);
    }

    public   void AddOrReplaceEvent(
        List<UnitOfWorkEventRecord> eventRecords,
        UnitOfWorkEventRecord eventRecord,
        Predicate<UnitOfWorkEventRecord> replacementSelector = null)
    {
        if (replacementSelector == null)
        {
            eventRecords.Add(eventRecord);
        }
        else
        {
            var foundIndex = eventRecords.FindIndex(replacementSelector);
            if (foundIndex < 0)
            {
                eventRecords.Add(eventRecord);
            }
            else
            {
                eventRecords[foundIndex] = eventRecord;
            }
        }
    }

    protected   void OnCompletedAsync()
    {
        for (var handler in CompletedHandlers)
        {
            handler.Invoke();
        }
    }

    protected   void OnFailed()
    {
        Failed.InvokeSafely(this, new UnitOfWorkFailedEventArgs(this, _exception, _isRolledback));
    }

    protected   void OnDisposed()
    {
        Disposed.InvokeSafely(this, new UnitOfWorkEventArgs(this));
    }

    public   void Dispose()
    {
        if (IsDisposed)
        {
            return;
        }

        IsDisposed = true;

        DisposeTransactions();

        if (!IsCompleted || _exception != null)
        {
            OnFailed();
        }

        OnDisposed();
    }

    private void DisposeTransactions()
    {
        for (var transactionApi in GetAllActiveTransactionApis())
        {
            try
            {
                transactionApi.Dispose();
            }
            catch
            {
            }
        }
    }

    private void PreventMultipleComplete()
    {
        if (IsCompleted || _isCompleting)
        {
            throw new AbpException("Complete is called before!");
        }
    }

    protected   void RollbackAllAsync(CancellationToken cancellationToken)
    {
        for (var databaseApi in GetAllActiveDatabaseApis())
        {
            if (databaseApi is ISupportsRollback)
            {
                try
                {
                    (databaseApi as ISupportsRollback).RollbackAsync(cancellationToken);
                }
                catch { }
            }
        }

        for (var transactionApi in GetAllActiveTransactionApis())
        {
            if (transactionApi is ISupportsRollback)
            {
                try
                {
                    (transactionApi as ISupportsRollback).RollbackAsync(cancellationToken);
                }
                catch { }
            }
        }
    }

    protected   void CommitTransactionsAsync()
    {
        for (var transaction in GetAllActiveTransactionApis())
        {
            transaction.CommitAsync();
        }
    }

    @Override public String toString()
    {
        return $"[UnitOfWork {Id}]";
    }
}
