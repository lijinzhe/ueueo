package com.ueueo.eventbus.distributed;

import com.ueueo.backgroundworkers.IBackgroundWorker;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OutboxSenderManager implements IBackgroundWorker {
    protected AbpDistributedEventBusOptions options;
    protected IOutboxSender outboxSender;
    protected List<IOutboxSender> senders;

    public OutboxSenderManager(
            AbpDistributedEventBusOptions options,
            IOutboxSender outboxSender) {
        this.outboxSender = outboxSender;
        this.options = options;
        this.senders = new ArrayList<>();
    }

    @Override
    public void start() {
        for (OutboxConfig outboxConfig : options.getOutboxes().values()) {
            if (outboxConfig.isSendingEnabled()) {
                outboxSender.start(outboxConfig);
                senders.add(outboxSender);
            }
        }
    }

    @Override
    public void stop() {
        for (IOutboxSender sender : senders) {
            sender.stop();
        }
    }

}
