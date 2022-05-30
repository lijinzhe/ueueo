﻿using System.Threading.Tasks;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Options;
using Volo.Abp.BackgroundWorkers;
using Volo.Abp.DistributedLocking;
using Volo.Abp.Guids;
using Volo.Abp.Modularity;
using Volo.Abp.Threading;
using Volo.Abp.Timing;

namespace Volo.Abp.BackgroundJobs;

[DependsOn(
    typeof(AbpBackgroundJobsAbstractionsModule),
    typeof(AbpBackgroundWorkersModule),
    typeof(AbpTimingModule),
    typeof(AbpGuidsModule),
    typeof(AbpDistributedLockingAbstractionsModule)
    )]
public class AbpBackgroundJobsModule : AbpModule
{
    @Override
    public void OnApplicationInitializationAsync(ApplicationInitializationContext context)
    {
        var options = context.ServiceProvider.GetRequiredService<IOptions<AbpBackgroundJobOptions>>().Value;
        if (options.IsJobExecutionEnabled)
        {
            context.AddBackgroundWorkerAsync<IBackgroundJobWorker>();
        }
    }

    @Override
    public void OnApplicationInitialization(ApplicationInitializationContext context)
    {
        AsyncHelper.RunSync(() => OnApplicationInitializationAsync(context));
    }
}
