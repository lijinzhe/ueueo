package com.ueueo.eventbus.distributed;

import java.util.HashMap;
import java.util.function.Consumer;

public class OutboxConfigDictionary extends HashMap<String, OutboxConfig> {
    public void Configure(Consumer<OutboxConfig> configAction) {
        Configure("Default", configAction);
    }

    public void Configure(String outboxName, Consumer<OutboxConfig> configAction) {
        OutboxConfig outboxConfig = computeIfAbsent(outboxName, key -> new OutboxConfig(outboxName));
        configAction.accept(outboxConfig);
    }
}
