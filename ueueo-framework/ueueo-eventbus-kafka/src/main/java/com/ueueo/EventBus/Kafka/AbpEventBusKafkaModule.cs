using Microsoft.Extensions.DependencyInjection;
using Volo.Abp.Kafka;
using Volo.Abp.Modularity;

namespace Volo.Abp.EventBus.Kafka;

[DependsOn(
    typeof(AbpEventBusModule),
    typeof(AbpKafkaModule))]
public class AbpEventBusKafkaModule : AbpModule
{
    @Override
    public void ConfigureServices(ServiceConfigurationContext context)
    {
        var configuration = context.Services.GetConfiguration();

        Configure<AbpKafkaEventBusOptions>(configuration.GetSection("Kafka:EventBus"));
    }

    @Override
    public void OnApplicationInitialization(ApplicationInitializationContext context)
    {
        context
            .ServiceProvider
            .GetRequiredService<KafkaDistributedEventBus>()
            .Initialize();
    }
}
