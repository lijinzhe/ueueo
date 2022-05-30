namespace Volo.Abp.RabbitMQ;

public static class RabbitMqConsts
{
    public static class DeliveryModes
    {
        public const int NonPersistent = 1;

        public const int Persistent = 2;
    }

    public static class ExchangeTypes
    {
        public const String Direct = "direct";

        public const String Topic = "topic";

        public const String Fanout = "fanout";

        public const String Headers = "headers";
    }
}
