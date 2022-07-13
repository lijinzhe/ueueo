using System;
using System.Collections.Generic;
using RabbitMQ.Client;
using Volo.Abp.RabbitMQ;

namespace Volo.Abp.BackgroundJobs.RabbitMQ;

public class JobQueueConfiguration : QueueDeclareConfiguration
{
    public Type JobArgsType;//  { get; }

    public String ConnectionName;// { get; set; }

    public String DelayedQueueName;// { get; set; }

    public JobQueueConfiguration(
        Type jobArgsType,
        String queueName,
        String delayedQueueName,
        String connectionName = null,
        boolean durable = true,
        boolean exclusive = false,
        boolean autoDelete = false)
        : base(
            queueName,
            durable,
            exclusive,
            autoDelete)
    {
        JobArgsType = jobArgsType;
        ConnectionName = connectionName;
        DelayedQueueName = delayedQueueName;
    }

    public   QueueDeclareOk DeclareDelayed(IModel channel)
    {
        var delayedArguments = new Dictionary<String, Object>(Arguments)
        {
            ["x-dead-letter-routing-key"] = QueueName,
            ["x-dead-letter-exchange"] = String.Empty
        };

        return channel.QueueDeclare(
            queue: DelayedQueueName,
            durable: Durable,
            exclusive: Exclusive,
            autoDelete: AutoDelete,
            arguments: delayedArguments
        );
    }
}
