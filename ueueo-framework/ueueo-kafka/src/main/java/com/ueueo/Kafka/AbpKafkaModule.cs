using Microsoft.Extensions.DependencyInjection;
using Volo.Abp.Json;
using Volo.Abp.Modularity;
using Volo.Abp.Threading;

namespace Volo.Abp.Kafka;

[DependsOn(
    typeof(AbpJsonModule),
    typeof(AbpThreadingModule)
)]
public class AbpKafkaModule : AbpModule
{
    @Override
    public void ConfigureServices(ServiceConfigurationContext context)
    {
        var configuration = context.Services.GetConfiguration();
        Configure<AbpKafkaOptions>(configuration.GetSection("Kafka"));
    }

    @Override
    public void OnApplicationShutdown(ApplicationShutdownContext context)
    {
        context.ServiceProvider
            .GetRequiredService<IConsumerPool>()
            .Dispose();

        context.ServiceProvider
            .GetRequiredService<IProducerPool>()
            .Dispose();
    }
}
