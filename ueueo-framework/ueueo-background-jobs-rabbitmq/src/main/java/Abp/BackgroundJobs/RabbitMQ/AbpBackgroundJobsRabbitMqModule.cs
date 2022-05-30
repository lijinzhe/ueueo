using System.Threading.Tasks;
using Microsoft.Extensions.DependencyInjection;
using Volo.Abp.Modularity;
using Volo.Abp.RabbitMQ;
using Volo.Abp.Threading;

namespace Volo.Abp.BackgroundJobs.RabbitMQ;

[DependsOn(
    typeof(AbpBackgroundJobsAbstractionsModule),
    typeof(AbpRabbitMqModule),
    typeof(AbpThreadingModule)
)]
public class AbpBackgroundJobsRabbitMqModule : AbpModule
{
    @Override
    public void ConfigureServices(ServiceConfigurationContext context)
    {
        context.Services.AddSingleton(typeof(IJobQueue<>), typeof(JobQueue<>));
    }

    @Override
    public void OnApplicationInitializationAsync(ApplicationInitializationContext context)
    {
        StartJobQueueManagerAsync(context);
    }

    @Override
    public void OnApplicationShutdownAsync(ApplicationShutdownContext context)
    {
        StopJobQueueManagerAsync(context);
    }

    @Override
    public void OnApplicationInitialization(ApplicationInitializationContext context)
    {
        AsyncHelper.RunSync(() => OnApplicationInitializationAsync(context));
    }

    @Override
    public void OnApplicationShutdown(ApplicationShutdownContext context)
    {
        AsyncHelper.RunSync(() => OnApplicationShutdownAsync(context));
    }

    private static void StartJobQueueManagerAsync(ApplicationInitializationContext context)
    {
        context.ServiceProvider
            .GetRequiredService<IJobQueueManager>()
            .StartAsync();
    }

    private static void StopJobQueueManagerAsync(ApplicationShutdownContext context)
    {
        context.ServiceProvider
            .GetRequiredService<IJobQueueManager>()
            .StopAsync();
    }
}
