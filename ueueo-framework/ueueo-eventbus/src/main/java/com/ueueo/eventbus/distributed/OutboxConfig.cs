using System;
using JetBrains.Annotations;

namespace Volo.Abp.EventBus.Distributed;

public class OutboxConfig
{
    [NotNull]
    public String Name;//  { get; }

    public Type ImplementationType;// { get; set; }

    public Func<Type, bool> Selector;// { get; set; }

    /**
     * Used to enable/disable sending events from outbox to the message broker.
     * Default: true.
    */
    public boolean IsSendingEnabled;// { get; set; } = true;

    public OutboxConfig(@Nonnull String name)
    {
        Name = Check.NotNullOrWhiteSpace(name, nameof(name));
    }
}
