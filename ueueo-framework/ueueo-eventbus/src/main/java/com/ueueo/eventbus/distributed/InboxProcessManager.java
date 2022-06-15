package com.ueueo.eventbus.distributed;

import com.ueueo.backgroundworkers.IBackgroundWorker;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class InboxProcessManager implements IBackgroundWorker {
    protected AbpDistributedEventBusOptions options;
    protected IInboxProcessor inboxProcessor;
    protected List<IInboxProcessor> processors;

    public InboxProcessManager(
            AbpDistributedEventBusOptions options,
            IInboxProcessor inboxProcessor) {
        this.inboxProcessor = inboxProcessor;
        this.options = options;
        this.processors = new ArrayList<>();
    }

    @Override
    public void start() {
        for (InboxConfig inboxConfig : options.getInboxes().values()) {
            if (inboxConfig.isProcessingEnabled()) {
                inboxProcessor.start(inboxConfig);
                processors.add(inboxProcessor);
            }
        }
    }

    @Override
    public void stop() {
        for (IInboxProcessor processor : processors) {
            processor.stop();
        }
    }
}
