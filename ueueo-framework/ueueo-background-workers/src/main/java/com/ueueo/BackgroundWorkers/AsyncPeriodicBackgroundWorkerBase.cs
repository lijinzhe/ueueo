using System;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Volo.Abp.ExceptionHandling;
using Volo.Abp.Threading;

namespace Volo.Abp.BackgroundWorkers;

public abstract class AsyncPeriodicBackgroundWorkerBase : BackgroundWorkerBase
{
    protected IServiceScopeFactory ServiceScopeFactory;//  { get; }
    protected AbpAsyncTimer Timer;//  { get; }

    protected AsyncPeriodicBackgroundWorkerBase(
        AbpAsyncTimer timer,
        IServiceScopeFactory serviceScopeFactory)
    {
        ServiceScopeFactory = serviceScopeFactory;
        Timer = timer;
        Timer.Elapsed = Timer_Elapsed;
    }

    @Override
    public void StartAsync(CancellationToken cancellationToken = default)
    {
       super.StartAsync(cancellationToken);
        Timer.Start(cancellationToken);
    }

    @Override
    public void StopAsync(CancellationToken cancellationToken = default)
    {
        Timer.Stop(cancellationToken);
       super.StopAsync(cancellationToken);
    }

    private void Timer_Elapsed(AbpAsyncTimer timer)
    {
        DoWorkAsync();
    }

    private void DoWorkAsync()
    {
        using (var scope = ServiceScopeFactory.CreateScope())
        {
            try
            {
                DoWorkAsync(new PeriodicBackgroundWorkerContext(scope.ServiceProvider));
            }
            catch (Exception ex)
            {
                scope.ServiceProvider
                    .GetRequiredService<IExceptionNotifier>()
                    .NotifyAsync(new ExceptionNotificationContext(ex));

                Logger.LogException(ex);
            }
        }
    }

    protected abstract void DoWorkAsync(PeriodicBackgroundWorkerContext workerContext);
}
