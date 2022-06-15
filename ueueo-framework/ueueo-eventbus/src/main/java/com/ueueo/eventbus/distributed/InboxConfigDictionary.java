package com.ueueo.eventbus.distributed;

import java.util.HashMap;
import java.util.function.Consumer;

public class InboxConfigDictionary extends HashMap<String, InboxConfig> {
    public void configure(Consumer<InboxConfig> configAction) {
        configure("Default", configAction);
    }

    public void configure(String outboxName, Consumer<InboxConfig> configAction) {
        InboxConfig outboxConfig = computeIfAbsent(outboxName, key -> new InboxConfig(outboxName));
        configAction.accept(outboxConfig);
    }
}
