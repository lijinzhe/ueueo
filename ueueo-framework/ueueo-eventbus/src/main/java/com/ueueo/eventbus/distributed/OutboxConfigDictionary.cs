using System;
using System.Collections.Generic;

namespace Volo.Abp.EventBus.Distributed;

public class OutboxConfigDictionary : Dictionary<String, OutboxConfig>
{
    public void Configure(Action<OutboxConfig> configAction)
    {
        Configure("Default", configAction);
    }

    public void Configure(String outboxName, Action<OutboxConfig> configAction)
    {
        var outboxConfig = this.GetOrAdd(outboxName, () => new OutboxConfig(outboxName));
        configAction(outboxConfig);
    }
}