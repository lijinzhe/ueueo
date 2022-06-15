package com.ueueo.eventbus.distributed;

import com.ueueo.distributedlocking.IDistributedLock;
import com.ueueo.distributedlocking.IDistributedLockHandle;
import com.ueueo.threading.CancellationToken;
import com.ueueo.uow.IUnitOfWorkManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class OutboxSender implements IOutboxSender {
    protected BeanFactory beanFactory;
    private ThreadPoolTaskScheduler timer;
    protected IDistributedEventBus distributedEventBus;
    protected IDistributedLock distributedLock;
    protected IEventOutbox outbox;
    protected OutboxConfig outboxConfig;
    protected AbpEventBusBoxesOptions eventBusBoxesOptions;
    protected IUnitOfWorkManager unitOfWorkManager;
    protected CancellationToken stoppingToken;

    public OutboxSender(
            BeanFactory beanFactory,
            ThreadPoolTaskScheduler timer,
            IDistributedEventBus distributedEventBus,
            IDistributedLock distributedLock,
            IUnitOfWorkManager unitOfWorkManager,
            AbpEventBusBoxesOptions eventBusBoxesOptions,
            CancellationToken stoppingToken) {
        this.beanFactory = beanFactory;
        this.timer = timer;
        this.distributedEventBus = distributedEventBus;
        this.distributedLock = distributedLock;
        this.unitOfWorkManager = unitOfWorkManager;
        this.eventBusBoxesOptions = eventBusBoxesOptions;
        this.stoppingToken = stoppingToken;
    }

    @Override
    public void start(OutboxConfig outboxConfig) {
        this.outboxConfig = outboxConfig;
        outbox = (IEventOutbox) beanFactory.getBean(outboxConfig.getImplementationType());
        timer.scheduleAtFixedRate(this::run, eventBusBoxesOptions.getPeriodTimeSpan().toMillis());
    }

    @Override
    public void stop() {
        stoppingToken.cancel();
        timer.shutdown();
    }

    public String getDistributedLockName() {
        return "AbpOutbox_" + outboxConfig.getName();
    }

    protected void run() {
        if (stoppingToken.isCancellationRequested()) {
            return;
        }

        IDistributedLockHandle handle = distributedLock.tryAcquire(getDistributedLockName(), eventBusBoxesOptions.getDistributedLockWaitDuration());
        if (handle != null) {
            while (true) {
                List<OutgoingEventInfo> waitingEvents = outbox.getWaitingEvents(eventBusBoxesOptions.getOutboxWaitingEventMaxCount(), stoppingToken);
                if (waitingEvents.size() <= 0) {
                    break;
                }

                log.info("Found {} events in the outbox.", waitingEvents.size());

                if (eventBusBoxesOptions.isBatchPublishOutboxEvents()) {
                    publishOutgoingMessagesInBatch(waitingEvents);
                } else {
                    publishOutgoingMessages(waitingEvents);
                }
            }
            handle.dispose();
        } else {
            log.debug("Could not obtain the distributed lock: " + getDistributedLockName());
            try {
                Thread.sleep(eventBusBoxesOptions.getDistributedLockWaitDuration().toMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected void publishOutgoingMessages(List<OutgoingEventInfo> waitingEvents) {
        for (OutgoingEventInfo waitingEvent : waitingEvents) {
            distributedEventBus
                    .asSupportsEventBoxes()
                    .publishFromOutbox(waitingEvent, outboxConfig);

            outbox.delete(waitingEvent.getId());

            log.info("Sent the event to the message broker with id = {}", waitingEvent.getId().toString());
        }
    }

    protected void publishOutgoingMessagesInBatch(List<OutgoingEventInfo> waitingEvents) {
        distributedEventBus
                .asSupportsEventBoxes()
                .publishManyFromOutbox(waitingEvents, outboxConfig);

        outbox.deleteMany(waitingEvents.stream().map(OutgoingEventInfo::getId).collect(Collectors.toList()));

        log.info("Sent {} events to message broker", waitingEvents.size());
    }

}
