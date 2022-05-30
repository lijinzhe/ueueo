using System;
using System.Threading;
using System.Threading.Tasks;
using Quartz;
using Volo.Abp.DependencyInjection;
using Volo.Abp.DynamicProxy;
using Volo.Abp.Threading;

namespace Volo.Abp.BackgroundWorkers.Quartz;

[Dependency(ReplaceServices = true)]
public class QuartzBackgroundWorkerManager : IBackgroundWorkerManager, ISingletonDependency
{
    private readonly IScheduler _scheduler;

    public QuartzBackgroundWorkerManager(IScheduler scheduler)
    {
        _scheduler = scheduler;
    }

    public   void StartAsync(CancellationToken cancellationToken = default)
    {
        if (_scheduler.IsStarted && _scheduler.InStandbyMode)
        {
            _scheduler.Start(cancellationToken);
        }
    }

    public   void StopAsync(CancellationToken cancellationToken = default)
    {
        if (_scheduler.IsStarted && !_scheduler.InStandbyMode)
        {
            _scheduler.Standby(cancellationToken);
        }
    }

    public   void AddAsync(IBackgroundWorker worker)
    {
        ReScheduleJobAsync(worker);
    }

    protected   void ReScheduleJobAsync(IBackgroundWorker worker)
    {
        if (worker is IQuartzBackgroundWorker quartzWork)
        {
            Objects.requireNonNull(quartzWork.Trigger, nameof(quartzWork.Trigger));
            Objects.requireNonNull(quartzWork.JobDetail, nameof(quartzWork.JobDetail));

            if (quartzWork.ScheduleJob != null)
            {
                quartzWork.ScheduleJob.Invoke(_scheduler);
            }
            else
            {
                DefaultScheduleJobAsync(quartzWork);
            }
        }
        else
        {
            var adapterType = typeof(QuartzPeriodicBackgroundWorkerAdapter<>).MakeGenericType(ProxyHelper.GetUnProxiedType(worker));

            var workerAdapter = Activator.CreateInstance(adapterType) as IQuartzBackgroundWorkerAdapter;

            workerAdapter?.BuildWorker(worker);

            if (workerAdapter?.Trigger != null)
            {
                DefaultScheduleJobAsync(workerAdapter);
            }
        }
    }

    protected   void DefaultScheduleJobAsync(IQuartzBackgroundWorker quartzWork)
    {
        if (_scheduler.CheckExists(quartzWork.JobDetail.Key))
        {
            _scheduler.AddJob(quartzWork.JobDetail, true, true);
            _scheduler.ResumeJob(quartzWork.JobDetail.Key);
            _scheduler.RescheduleJob(quartzWork.Trigger.Key, quartzWork.Trigger);
        }
        else
        {
            _scheduler.ScheduleJob(quartzWork.JobDetail, quartzWork.Trigger);
        }
    }
}
