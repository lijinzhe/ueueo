using System;
using RabbitMQ.Client;

namespace Volo.Abp.RabbitMQ;

public interface IConnectionPool : IDisposable
{
    IConnection Get(String connectionName = null);
}
