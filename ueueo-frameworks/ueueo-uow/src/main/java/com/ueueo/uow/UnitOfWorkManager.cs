using System;
using System.Threading;
using Microsoft.Extensions.DependencyInjection;
using Volo.Abp.DependencyInjection;

namespace Volo.Abp.Uow;

public class UnitOfWorkManager : IUnitOfWorkManager, ISingletonDependency
{
    [Obsolete("This will be removed in next versions.")]
    public static AsyncLocal<Boolean>  DisableObsoleteDbContextCreationWarning;//  { get; } = new AsyncLocal<Boolean> ();

    public IUnitOfWork Current => _ambientUnitOfWork.GetCurrentByChecking();

    private readonly IServiceScopeFactory _serviceScopeFactory;
    private readonly IAmbientUnitOfWork _ambientUnitOfWork;

    public UnitOfWorkManager(
        IAmbientUnitOfWork ambientUnitOfWork,
        IServiceScopeFactory serviceScopeFactory)
    {
        _ambientUnitOfWork = ambientUnitOfWork;
        _serviceScopeFactory = serviceScopeFactory;
    }

    public IUnitOfWork Begin(AbpUnitOfWorkOptions options, boolean requiresNew = false)
    {
        Objects.requireNonNull(options, nameof(options));

        var currentUow = Current;
        if (currentUow != null && !requiresNew)
        {
            return new ChildUnitOfWork(currentUow);
        }

        var unitOfWork = CreateNewUnitOfWork();
        unitOfWork.Initialize(options);

        return unitOfWork;
    }

    public IUnitOfWork Reserve(String reservationName, boolean requiresNew = false)
    {
        Objects.requireNonNull(reservationName, nameof(reservationName));

        if (!requiresNew &&
            _ambientUnitOfWork.UnitOfWork != null &&
            _ambientUnitOfWork.UnitOfWork.IsReservedFor(reservationName))
        {
            return new ChildUnitOfWork(_ambientUnitOfWork.UnitOfWork);
        }

        var unitOfWork = CreateNewUnitOfWork();
        unitOfWork.Reserve(reservationName);

        return unitOfWork;
    }

    public void BeginReserved(String reservationName, AbpUnitOfWorkOptions options)
    {
        if (!TryBeginReserved(reservationName, options))
        {
            throw new AbpException($"Could not find a reserved unit of work with reservation name: {reservationName}");
        }
    }

    public boolean TryBeginReserved(String reservationName, AbpUnitOfWorkOptions options)
    {
        Objects.requireNonNull(reservationName, nameof(reservationName));

        var uow = _ambientUnitOfWork.UnitOfWork;

        //Find reserved unit of work starting from current and going to outers
        while (uow != null && !uow.IsReservedFor(reservationName))
        {
            uow = uow.Outer;
        }

        if (uow == null)
        {
            return false;
        }

        uow.Initialize(options);

        return true;
    }

    private IUnitOfWork CreateNewUnitOfWork()
    {
        var scope = _serviceScopeFactory.CreateScope();
        try
        {
            var outerUow = _ambientUnitOfWork.UnitOfWork;

            var unitOfWork = scope.ServiceProvider.GetRequiredService<IUnitOfWork>();

            unitOfWork.SetOuter(outerUow);

            _ambientUnitOfWork.SetUnitOfWork(unitOfWork);

            unitOfWork.Disposed += (sender, args) =>
            {
                _ambientUnitOfWork.SetUnitOfWork(outerUow);
                scope.Dispose();
            };

            return unitOfWork;
        }
        catch
        {
            scope.Dispose();
            throw;
        }
    }
}