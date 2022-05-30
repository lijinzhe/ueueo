namespace Volo.Abp.Domain.Entities.Events;

/**
 * Null-object implementation of <see cref="IEntityChangeEventHelper"/>.
*/
public class NullEntityChangeEventHelper : IEntityChangeEventHelper
{
    /**
     * Gets single instance of <see cref="NullEntityChangeEventHelper"/> class.
    */
    public static NullEntityChangeEventHelper Instance;//  { get; } = new NullEntityChangeEventHelper();

    private NullEntityChangeEventHelper()
    {
    }

    public void PublishEntityCreatingEvent(Object entity)
    {
    }

    public void PublishEntityCreatedEvent(Object entity)
    {
    }

    public void PublishEntityUpdatingEvent(Object entity)
    {
    }

    public void PublishEntityUpdatedEvent(Object entity)
    {
    }

    public void PublishEntityDeletingEvent(Object entity)
    {
    }

    public void PublishEntityDeletedEvent(Object entity)
    {
    }
}
