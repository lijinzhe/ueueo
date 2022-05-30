using System;
using JetBrains.Annotations;

namespace Volo.Abp.EventBus.Distributed;

public class InboxConfig
{
    [NotNull]
    public String Name;//  { get; }

    public Type ImplementationType;// { get; set; }

    public Func<Type, bool> EventSelector;// { get; set; }

    public Func<Type, bool> HandlerSelector;// { get; set; }

    /**
     * Used to enable/disable processing incoming events.
     * Default: true.
    */
    public boolean IsProcessingEnabled;// { get; set; } = true;

    public InboxConfig(@Nonnull String name)
    {
        Name = Check.NotNullOrWhiteSpace(name, nameof(name));
    }
}
