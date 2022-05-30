using System;
using System.Collections.Generic;

namespace Volo.Abp.EventBus.Distributed;

public class InboxConfigDictionary : Dictionary<String, InboxConfig>
{
    public void Configure(Action<InboxConfig> configAction)
    {
        Configure("Default", configAction);
    }

    public void Configure(String outboxName, Action<InboxConfig> configAction)
    {
        var outboxConfig = this.GetOrAdd(outboxName, () => new InboxConfig(outboxName));
        configAction(outboxConfig);
    }
}
