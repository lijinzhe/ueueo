package com.ueueo.eventbus.distributed;

public interface IInboxProcessor {
    void start(InboxConfig inboxConfig);

    void stop();
}
