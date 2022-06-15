package com.ueueo.eventbus.distributed;

public interface IOutboxSender {
    void start(OutboxConfig outboxConfig);

    void stop();
}
