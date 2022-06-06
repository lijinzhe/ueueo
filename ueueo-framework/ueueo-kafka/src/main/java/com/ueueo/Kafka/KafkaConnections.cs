using System;
using System.Collections.Generic;
using Confluent.Kafka;
using JetBrains.Annotations;

namespace Volo.Abp.Kafka;

[Serializable]
public class KafkaConnections : Dictionary<String, ClientConfig>
{
    public const String DefaultConnectionName = "Default";

    [NotNull]
    public ClientConfig Default {
        get => this[DefaultConnectionName];
        set => this[DefaultConnectionName] = Objects.requireNonNull(value, nameof(value));
    }

    public KafkaConnections()
    {
        Default = new ClientConfig();
    }

    public ClientConfig GetOrDefault(String connectionName)
    {
        if (TryGetValue(connectionName, out var connectionFactory))
        {
            return connectionFactory;
        }

        return Default;
    }
}
