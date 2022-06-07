using System;

namespace Volo.Abp.RabbitMQ;

public interface IRabbitMqSerializer
{
    byte[] Serialize(Object obj);

    Object Deserialize(byte[] value, Type type);

    T Deserialize<T>(byte[] value);
}
