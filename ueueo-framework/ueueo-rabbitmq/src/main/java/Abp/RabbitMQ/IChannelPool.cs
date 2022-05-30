using System;

namespace Volo.Abp.RabbitMQ;

public interface IChannelPool : IDisposable
{
    IChannelAccessor Acquire(String channelName = null, String connectionName = null);
}
