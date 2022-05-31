package com.ueueo.eventbus.distributed;

import com.ueueo.threading.CancellationToken;

public interface IInboxProcessor {
    void start(InboxConfig inboxConfig, CancellationToken cancellationToken);

    void stop(CancellationToken cancellationToken);
}
