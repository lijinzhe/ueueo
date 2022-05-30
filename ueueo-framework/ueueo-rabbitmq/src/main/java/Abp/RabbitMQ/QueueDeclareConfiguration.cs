using System.Collections.Generic;
using JetBrains.Annotations;
using RabbitMQ.Client;

namespace Volo.Abp.RabbitMQ;

public class QueueDeclareConfiguration
{
    @Nonnull public String QueueName;//  { get; }

    public boolean Durable;// { get; set; }

    public boolean Exclusive;// { get; set; }

    public boolean AutoDelete;// { get; set; }

    public IDictionary<String, Object> Arguments;//  { get; }

    public QueueDeclareConfiguration(
        @Nonnull String queueName,
        boolean durable = true,
        boolean exclusive = false,
        boolean autoDelete = false)
    {
        QueueName = queueName;
        Durable = durable;
        Exclusive = exclusive;
        AutoDelete = autoDelete;
        Arguments = new Dictionary<String, Object>();
    }

    public   QueueDeclareOk Declare(IModel channel)
    {
        return channel.QueueDeclare(
            queue: QueueName,
            durable: Durable,
            exclusive: Exclusive,
            autoDelete: AutoDelete,
            arguments: Arguments
        );
    }
}
