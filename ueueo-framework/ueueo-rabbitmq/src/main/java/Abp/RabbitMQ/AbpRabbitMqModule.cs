using Microsoft.Extensions.DependencyInjection;
using Volo.Abp.Json;
using Volo.Abp.Modularity;
using Volo.Abp.Threading;

namespace Volo.Abp.RabbitMQ;

[DependsOn(
    typeof(AbpJsonModule),
    typeof(AbpThreadingModule)
    )]
public class AbpRabbitMqModule : AbpModule
{
    @Override
    public void ConfigureServices(ServiceConfigurationContext context)
    {
        var configuration = context.Services.GetConfiguration();
        Configure<AbpRabbitMqOptions>(configuration.GetSection("RabbitMQ"));
        Configure<AbpRabbitMqOptions>(options =>
        {
            for (var connectionFactory in options.Connections.Values)
            {
                connectionFactory.DispatchConsumersAsync = true;
            }
        });
    }

    @Override
    public void OnApplicationShutdown(ApplicationShutdownContext context)
    {
        context.ServiceProvider
            .GetRequiredService<IChannelPool>()
            .Dispose();

        context.ServiceProvider
            .GetRequiredService<IConnectionPool>()
            .Dispose();
    }
}
