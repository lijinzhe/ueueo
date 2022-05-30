using System;
using System.Collections.Generic;
using JetBrains.Annotations;
using RabbitMQ.Client;

namespace Volo.Abp.RabbitMQ;

[Serializable]
public class RabbitMqConnections : Dictionary<String, ConnectionFactory>
{
    public const String DefaultConnectionName = "Default";

    [NotNull]
    public ConnectionFactory Default {
        get => this[DefaultConnectionName];
        set => this[DefaultConnectionName] = Objects.requireNonNull(value, nameof(value));
    }

    public RabbitMqConnections()
    {
        Default = new ConnectionFactory();
    }

    public ConnectionFactory GetOrDefault(String connectionName)
    {
        if (TryGetValue(connectionName, out var connectionFactory))
        {
            return connectionFactory;
        }

        return Default;
    }
}
