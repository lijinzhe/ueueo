namespace Volo.Abp.Kafka;

public interface IKafkaMessageConsumerFactory
{
    /**
     * Creates a new <see cref="IKafkaMessageConsumer"/>.
     * Avoid to create too many consumers since they are
     * not disposed until end of the application.
    *
     * <param name="topicName"></param>
     * <param name="groupId"></param>
     * <param name="connectionName"></param>
     * <returns></returns>
     */
    IKafkaMessageConsumer Create(
        String topicName,
        String groupId,
        String connectionName = null);
}
