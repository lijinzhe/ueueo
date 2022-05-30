using System;
using System.Threading.Tasks;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;

namespace Volo.Abp.RabbitMQ;

public interface IRabbitMqMessageConsumer
{
    void BindAsync(String routingKey);

    void UnbindAsync(String routingKey);

    void OnMessageReceived(Func<IModel, BasicDeliverEventArgs, Task> callback);
}
