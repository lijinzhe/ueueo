using System;
using System.Threading.Tasks;
using Confluent.Kafka;

namespace Volo.Abp.Kafka;

public interface IKafkaMessageConsumer
{
    void OnMessageReceived(Func<Message<String, byte[]>, Task> callback);
}
