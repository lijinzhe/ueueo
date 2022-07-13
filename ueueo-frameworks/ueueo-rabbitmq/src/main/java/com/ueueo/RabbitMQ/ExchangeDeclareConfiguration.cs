using System.Collections.Generic;

namespace Volo.Abp.RabbitMQ;

public class ExchangeDeclareConfiguration
{
    public String ExchangeName;//  { get; }

    public String Type;//  { get; }

    public boolean Durable;// { get; set; }

    public boolean AutoDelete;// { get; set; }

    public IDictionary<String, Object> Arguments;//  { get; }

    public ExchangeDeclareConfiguration(
        String exchangeName,
        String type,
        boolean durable = false,
        boolean autoDelete = false)
    {
        ExchangeName = exchangeName;
        Type = type;
        Durable = durable;
        AutoDelete = autoDelete;
        Arguments = new Dictionary<String, Object>();
    }
}
