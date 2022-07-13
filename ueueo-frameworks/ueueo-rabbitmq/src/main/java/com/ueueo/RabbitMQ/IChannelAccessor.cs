using System;
using RabbitMQ.Client;

namespace Volo.Abp.RabbitMQ;

public interface IChannelAccessor : IDisposable
{
    /**
     * Reference to the channel.
     * Never dispose the <see cref="Channel"/> object.
     * Instead, dispose the <see cref="IChannelAccessor"/> after usage.
    */
    IModel Channel;//  { get; }

    /**
     * Name of the channel.
    */
    String Name;//  { get; }
}
