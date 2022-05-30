using System;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Volo.Abp.ExceptionHandling;
using Volo.Abp.Threading;

namespace Volo.Abp.BackgroundWorkers;

/**
 * Extends <see cref="BackgroundWorkerBase"/> to add a periodic running Timer.
*/
public abstract class PeriodicBackgroundWorkerBase : BackgroundWorkerBase
{
    protected IServiceScopeFactory ServiceScopeFactory;//  { get; }
    protected AbpTimer Timer;//  { get; }

    protected PeriodicBackgroundWorkerBase(
        AbpTimer timer,
        IServiceScopeFactory serviceScopeFactory)
    {
        ServiceScopeFactory = serviceScopeFactory;
        Timer = timer;
        Timer.Elapsed += Timer_Elapsed;
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

    private void Timer_Elapsed(Object sender, System.EventArgs e)
    {
        using (var scope = ServiceScopeFactory.CreateScope())
        {
            try
            {
                DoWork(new PeriodicBackgroundWorkerContext(scope.ServiceProvider));
            }
            catch (Exception ex)
            {
                var exceptionNotifier = scope.ServiceProvider.GetRequiredService<IExceptionNotifier>();
                AsyncHelper.RunSync(() => exceptionNotifier.NotifyAsync(new ExceptionNotificationContext(ex)));

                Logger.LogException(ex);
            }
        }
    }

    /**
     * Periodic works should be done by implementing this method.
    */
    protected abstract void DoWork(PeriodicBackgroundWorkerContext workerContext);
}
