using System;
using System.Threading.Tasks;

namespace Volo.Abp.EventBus.Distributed;

public interface IDistributedEventBus : IEventBus
{
    /**
     * Registers to an event.
     * Same (given) instance of the handler is used for all event occurrences.
    *
     * <typeparam name="TEvent">Event type</typeparam>
     * <param name="handler">Object to handle the event</param>
     */
    IDisposable Subscribe<TEvent>(IDistributedEventHandler<TEvent> handler)
        //where TEvent : class;

    void PublishAsync<TEvent>(
        TEvent eventData,
        boolean onUnitOfWorkComplete = true,
        boolean useOutbox = true)
        //where TEvent : class
        ;

    void PublishAsync(
        Type eventType,
        Object eventData,
        boolean onUnitOfWorkComplete = true,
        boolean useOutbox = true);
}
