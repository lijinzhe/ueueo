using System;
using Confluent.Kafka;

namespace Volo.Abp.Kafka;

public interface IConsumerPool : IDisposable
{
    IConsumer<String, byte[]> Get(String groupId, String connectionName = null);
}
