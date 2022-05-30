using System;
using System.Threading.Tasks;
using Volo.Abp.EventBus.Distributed;

namespace Volo.Abp.EventBus;

public delegate void EventHandlerMethodExecutorAsync(IEventHandler target, Object parameter);

public interface IEventHandlerMethodExecutor
{
    EventHandlerMethodExecutorAsync ExecutorAsync;//  { get; }
}

public class LocalEventHandlerMethodExecutor<TEvent> : IEventHandlerMethodExecutor
    where TEvent : class
{
    public  EventHandlerMethodExecutorAsync ExecutorAsync => (target, parameter) => target.As<ILocalEventHandler<TEvent>>().HandleEventAsync(parameter.As<TEvent>());

    public void ExecuteAsync(IEventHandler target, TEvent parameters)
    {
        return ExecutorAsync(target, parameters);
    }
}

public class DistributedEventHandlerMethodExecutor<TEvent> : IEventHandlerMethodExecutor
    where TEvent : class
{
    public EventHandlerMethodExecutorAsync ExecutorAsync => (target, parameter) => target.As<IDistributedEventHandler<TEvent>>().HandleEventAsync(parameter.As<TEvent>());

    public void ExecuteAsync(IEventHandler target, TEvent parameters)
    {
        return ExecutorAsync(target, parameters);
    }
}
