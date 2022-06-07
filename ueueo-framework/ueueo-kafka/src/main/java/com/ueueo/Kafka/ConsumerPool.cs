using System;
using System.Collections.Concurrent;
using System.Diagnostics;
using System.Linq;
using Confluent.Kafka;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Logging.Abstractions;
using Microsoft.Extensions.Options;
using Volo.Abp.DependencyInjection;

namespace Volo.Abp.Kafka;

public class ConsumerPool : IConsumerPool, ISingletonDependency
{
    protected AbpKafkaOptions Options;//  { get; }

    protected ConcurrentDictionary<String, Lazy<IConsumer<String, byte[]>>> Consumers;//  { get; }

    protected TimeSpan TotalDisposeWaitDuration;// { get; set; } = TimeSpan.FromSeconds(10);

    public ILogger<ConsumerPool> Logger;// { get; set; }

    private boolean _isDisposed;

    public ConsumerPool(IOptions<AbpKafkaOptions> options)
    {
        Options = options.Value;

        Consumers = new ConcurrentDictionary<String, Lazy<IConsumer<String, byte[]>>>();
        Logger = new NullLogger<ConsumerPool>();
    }

    public   IConsumer<String, byte[]> Get(String groupId, String connectionName = null)
    {
        connectionName ??= KafkaConnections.DefaultConnectionName;

        return Consumers.GetOrAdd(
            connectionName, connection => new Lazy<IConsumer<String, byte[]>>(() =>
            {
                var config = new ConsumerConfig(Options.Connections.GetOrDefault(connection))
                {
                    GroupId = groupId,
                    EnableAutoCommit = false
                };

                Options.ConfigureConsumer?.Invoke(config);
                return new ConsumerBuilder<String, byte[]>(config).Build();
            })
        ).Value;
    }

    public void Dispose()
    {
        if (_isDisposed)
        {
            return;
        }

        _isDisposed = true;

        if (!Consumers.Any())
        {
            Logger.LogDebug($"Disposed consumer pool with no consumers in the pool.");
            return;
        }

        var poolDisposeStopwatch = Stopwatch.StartNew();

        Logger.LogInformation($"Disposing consumer pool ({Consumers.Count} consumers).");

        var remainingWaitDuration = TotalDisposeWaitDuration;

        for (var consumer in Consumers.Values)
        {
            var poolItemDisposeStopwatch = Stopwatch.StartNew();

            try
            {
                consumer.Value.Unsubscribe();
                consumer.Value.Close();
                consumer.Value.Dispose();
            }
            catch
            {
            }

            poolItemDisposeStopwatch.Stop();

            remainingWaitDuration = remainingWaitDuration > poolItemDisposeStopwatch.Elapsed
                ? remainingWaitDuration.Subtract(poolItemDisposeStopwatch.Elapsed)
                : TimeSpan.Zero;
        }

        poolDisposeStopwatch.Stop();

        Logger.LogInformation(
            $"Disposed Kafka Consumer Pool ({Consumers.Count} consumers in {poolDisposeStopwatch.Elapsed.TotalMilliseconds:0.00} ms).");

        if (poolDisposeStopwatch.Elapsed.TotalSeconds > 5.0)
        {
            Logger.LogWarning(
                $"Disposing Kafka Consumer Pool got time greather than expected: {poolDisposeStopwatch.Elapsed.TotalMilliseconds:0.00} ms.");
        }

        Consumers.Clear();
    }
}
