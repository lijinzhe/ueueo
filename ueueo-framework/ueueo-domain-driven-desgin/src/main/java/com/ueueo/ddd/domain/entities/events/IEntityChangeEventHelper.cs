namespace Volo.Abp.Domain.Entities.Events;

/**
 * Used to trigger entity change events.
*/
public interface IEntityChangeEventHelper
{
    void PublishEntityCreatingEvent(Object entity);
    void PublishEntityCreatedEvent(Object entity);

    void PublishEntityUpdatingEvent(Object entity);
    void PublishEntityUpdatedEvent(Object entity);

    void PublishEntityDeletingEvent(Object entity);
    void PublishEntityDeletedEvent(Object entity);
}
