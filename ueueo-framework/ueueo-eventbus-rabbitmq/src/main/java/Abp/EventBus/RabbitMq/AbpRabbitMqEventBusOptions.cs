using Volo.Abp.RabbitMQ;

namespace Volo.Abp.EventBus.RabbitMq;

public class AbpRabbitMqEventBusOptions
{
    public const String DefaultExchangeType = RabbitMqConsts.ExchangeTypes.Direct;

    public String ConnectionName;// { get; set; }

    public String ClientName;// { get; set; }

    public String ExchangeName;// { get; set; }

    public String ExchangeType;// { get; set; }

    public String GetExchangeTypeOrDefault()
    {
        return String.IsNullOrEmpty(ExchangeType)
            ? DefaultExchangeType
            : ExchangeType;
    }
}
