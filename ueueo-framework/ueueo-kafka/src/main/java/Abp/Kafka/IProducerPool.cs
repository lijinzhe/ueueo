using System;
using Confluent.Kafka;

namespace Volo.Abp.Kafka;

public interface IProducerPool : IDisposable
{
    IProducer<String, byte[]> Get(String connectionName = null);
}
