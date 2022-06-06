using System.Linq;
using System.Threading.Tasks;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.DependencyInjection.Extensions;
using Microsoft.Extensions.Options;
using Volo.Abp.Modularity;
using Volo.Abp.Quartz;
using Volo.Abp.Threading;

namespace Volo.Abp.BackgroundWorkers.Quartz;

[DependsOn(
    typeof(AbpBackgroundWorkersModule),
    typeof(AbpQuartzModule)
)]
public class AbpBackgroundWorkersQuartzModule : AbpModule
{
    @Override
    public void PreConfigureServices(ServiceConfigurationContext context)
    {
        context.Services.AddConventionalRegistrar(new AbpQuartzConventionalRegistrar());
    }

    @Override
    public void ConfigureServices(ServiceConfigurationContext context)
    {
        context.Services.AddSingleton(typeof(QuartzPeriodicBackgroundWorkerAdapter<>));
    }

    @Override
    public void OnPreApplicationInitialization(ApplicationInitializationContext context)
    {
        var options = context.ServiceProvider.GetRequiredService<IOptions<AbpBackgroundWorkerOptions>>().Value;
        if (!options.IsEnabled)
        {
            var quartzOptions = context.ServiceProvider.GetRequiredService<IOptions<AbpQuartzOptions>>().Value;
            quartzOptions.StartSchedulerFactory = _ => Task.CompletedTask;
        }
    }

    @Override
    public void OnApplicationInitializationAsync(ApplicationInitializationContext context)
    {
        var quartzBackgroundWorkerOptions = context.ServiceProvider.GetRequiredService<IOptions<AbpBackgroundWorkerQuartzOptions>>().Value;
        if (quartzBackgroundWorkerOptions.IsAutoRegisterEnabled)
        {
            var backgroundWorkerManager = context.ServiceProvider.GetRequiredService<IBackgroundWorkerManager>();
            var works = context.ServiceProvider.GetServices<IQuartzBackgroundWorker>().Where(x => x.AutoRegister);

            for (var work in works)
            {
                backgroundWorkerManager.AddAsync(work);
            }
        }
    }

    @Override
    public void OnApplicationInitialization(ApplicationInitializationContext context)
    {
        AsyncHelper.RunSync(() => OnApplicationInitializationAsync(context));
    }
}
