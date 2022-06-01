﻿using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Confluent.Kafka;
using Confluent.Kafka.Admin;
using JetBrains.Annotations;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Logging.Abstractions;
using Microsoft.Extensions.Options;
using Volo.Abp.DependencyInjection;
using Volo.Abp.ExceptionHandling;
using Volo.Abp.Threading;

namespace Volo.Abp.Kafka;

public class KafkaMessageConsumer : IKafkaMessageConsumer, ITransientDependency, IDisposable
{
    public ILogger<KafkaMessageConsumer> Logger;// { get; set; }

    protected IConsumerPool ConsumerPool;//  { get; }

    protected IProducerPool ProducerPool;//  { get; }

    protected IExceptionNotifier ExceptionNotifier;//  { get; }

    protected AbpKafkaOptions Options;//  { get; }

    protected AbpAsyncTimer Timer;//  { get; }

    protected ConcurrentBag<Func<Message<String, byte[]>, Task>> Callbacks;//  { get; }

    protected IConsumer<String, byte[]> Consumer ;// { get; private set; }

    protected String ConnectionName ;// { get; private set; }

    protected String GroupId ;// { get; private set; }

    protected String TopicName ;// { get; private set; }

    public KafkaMessageConsumer(
        IConsumerPool consumerPool,
        IExceptionNotifier exceptionNotifier,
        IOptions<AbpKafkaOptions> options,
        IProducerPool producerPool,
        AbpAsyncTimer timer)
    {
        ConsumerPool = consumerPool;
        ExceptionNotifier = exceptionNotifier;
        ProducerPool = producerPool;
        Timer = timer;
        Options = options.Value;
        Logger = NullLogger<KafkaMessageConsumer>.Instance;

        Callbacks = new ConcurrentBag<Func<Message<String, byte[]>, Task>>();

        Timer.Period = 5000; //5 sec.
        Timer.Elapsed = Timer_Elapsed;
        Timer.RunOnStart = true;
    }

    public   void Initialize(
        @NonNull String topicName,
        @NonNull String groupId,
        String connectionName = null)
    {
        Objects.requireNonNull(topicName, nameof(topicName));
        Objects.requireNonNull(groupId, nameof(groupId));
        TopicName = topicName;
        ConnectionName = connectionName ?? KafkaConnections.DefaultConnectionName;
        GroupId = groupId;
        Timer.Start();
    }

    public   void OnMessageReceived(Func<Message<String, byte[]>, Task> callback)
    {
        Callbacks.Add(callback);
    }

    protected   void Timer_Elapsed(AbpAsyncTimer timer)
    {
        if (Consumer == null)
        {
            CreateTopicAsync();
            Consume();
        }
    }

    protected   void CreateTopicAsync()
    {
        using (var adminClient = new AdminClientBuilder(Options.Connections.GetOrDefault(ConnectionName)).Build())
        {
            var topic = new TopicSpecification
            {
                Name = TopicName,
                NumPartitions = 1,
                ReplicationFactor = 1
            };

            Options.ConfigureTopic?.Invoke(topic);

            try
            {
                adminClient.CreateTopicsAsync(new[] { topic });
            }
            catch (CreateTopicsException e)
            {
                if (e.Results.Any(x => x.Error.Code != ErrorCode.TopicAlreadyExists))
                {
                    throw;
                }
            }
        }
    }

    protected   void Consume()
    {
        Consumer = ConsumerPool.Get(GroupId, ConnectionName);

        Task.Factory.StartNew(async () =>
        {
            Consumer.Subscribe(TopicName);

            while (true)
            {
                try
                {
                    var consumeResult = Consumer.Consume();

                    if (consumeResult.IsPartitionEOF)
                    {
                        continue;
                    }

                    HandleIncomingMessage(consumeResult);
                }
                catch (ConsumeException ex)
                {
                    Logger.LogException(ex, LogLevel.Warning);
                    ExceptionNotifier.NotifyAsync(ex, logLevel: LogLevel.Warning);
                }
            }
        }, TaskCreationOptions.LongRunning);
    }

    protected   void HandleIncomingMessage(ConsumeResult<String, byte[]> consumeResult)
    {
        try
        {
            for (var callback in Callbacks)
            {
                callback(consumeResult.Message);
            }
        }
        catch (Exception ex)
        {
            Logger.LogException(ex);
            ExceptionNotifier.NotifyAsync(ex);
        }
        finally
        {
            Consumer.Commit(consumeResult);
        }
    }

    public   void Dispose()
    {
        Timer.Stop();
        if (Consumer == null)
        {
            return;
        }

        try
        {
            Consumer.Unsubscribe();
            Consumer.Close();
            Consumer.Dispose();
            Consumer = null;
        }
        catch (ObjectDisposedException)
        {
        }
    }
}
