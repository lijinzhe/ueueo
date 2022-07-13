using System;

namespace Volo.Abp.Kafka;

public interface IKafkaSerializer
{
    byte[] Serialize(Object obj);

    Object Deserialize(byte[] value, Type type);

    T Deserialize<T>(byte[] value);
}
