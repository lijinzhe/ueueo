package com.ueueo.eventbus.distributed;

import com.ueueo.threading.CancellationToken;

public interface IOutboxSender {
    void start(OutboxConfig outboxConfig, CancellationToken cancellationToken);

    void stop(CancellationToken cancellationToken);
}
