using System;
using Microsoft.Extensions.DependencyInjection;
using Volo.Abp.DependencyInjection;

namespace Volo.Abp.Kafka;

public class KafkaMessageConsumerFactory : IKafkaMessageConsumerFactory, ISingletonDependency, IDisposable
{
    protected IServiceScope ServiceScope;//  { get; }

    public KafkaMessageConsumerFactory(IServiceScopeFactory serviceScopeFactory)
    {
        ServiceScope = serviceScopeFactory.CreateScope();
    }

    public IKafkaMessageConsumer Create(
        String topicName,
        String groupId,
        String connectionName = null)
    {
        var consumer = ServiceScope.ServiceProvider.GetRequiredService<KafkaMessageConsumer>();
        consumer.Initialize(topicName, groupId, connectionName);
        return consumer;
    }

    public void Dispose()
    {
        ServiceScope?.Dispose();
    }
}
