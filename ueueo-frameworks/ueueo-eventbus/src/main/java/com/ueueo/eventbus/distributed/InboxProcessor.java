package com.ueueo.eventbus.distributed;

import com.ueueo.distributedlocking.IDistributedLock;
import com.ueueo.distributedlocking.IDistributedLockHandle;
import com.ueueo.threading.CancellationToken;
import com.ueueo.uow.AbpUnitOfWorkOptions;
import com.ueueo.uow.IUnitOfWork;
import com.ueueo.uow.IUnitOfWorkManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class InboxProcessor implements IInboxProcessor {
    protected BeanFactory beanFactory;
    protected IDistributedEventBus distributedEventBus;
    protected IDistributedLock distributedLock;
    protected IUnitOfWorkManager unitOfWorkManager;
    protected IEventInbox inbox;
    protected InboxConfig inboxConfig;
    protected AbpEventBusBoxesOptions eventBusBoxesOptions;

    protected Date lastCleanTime;

    protected CancellationToken stoppingToken;

    private ThreadPoolTaskScheduler timer;

    protected String getDistributedLockName() {
        return "AbpInbox_" + inboxConfig.getName();
    }

    public InboxProcessor(
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
    public void start(InboxConfig inboxConfig) {
        this.inboxConfig = inboxConfig;
        inbox = (IEventInbox) beanFactory.getBean(inboxConfig.getImplementationType());
        timer.scheduleAtFixedRate(this::run, eventBusBoxesOptions.getPeriodTimeSpan().toMillis());
    }

    @Override
    public void stop() {
        stoppingToken.cancel();
        timer.shutdown();
    }

    protected void run() {
        if (stoppingToken.isCancellationRequested()) {
            return;
        }

        IDistributedLockHandle handle = distributedLock.tryAcquire(getDistributedLockName(), eventBusBoxesOptions.getDistributedLockWaitDuration());
        if (handle != null) {
            deleteOldEventsAsync();

            while (true) {
                List<IncomingEventInfo> waitingEvents = inbox.getWaitingEvents(eventBusBoxesOptions.getInboxWaitingEventMaxCount(), stoppingToken);
                if (waitingEvents.size() <= 0) {
                    break;
                }

                log.info("Found {} events in the inbox.", waitingEvents.size());

                for (IncomingEventInfo waitingEvent : waitingEvents) {
                    AbpUnitOfWorkOptions options = new AbpUnitOfWorkOptions();
                    options.setTransactional(true);
                    IUnitOfWork uow = unitOfWorkManager.begin(options, true);

                    distributedEventBus
                            .asSupportsEventBoxes()
                            .processFromInbox(waitingEvent, inboxConfig);

                    inbox.markAsProcessed(waitingEvent.getId());

                    uow.complete();

                    log.info("Processed the incoming event with id = {waitingEvent.Id:N}");
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

    protected void deleteOldEventsAsync() {
        if (lastCleanTime != null && lastCleanTime.getTime() + eventBusBoxesOptions.getCleanOldEventTimeIntervalSpan().toMillis() > System.currentTimeMillis()) {
            return;
        }

        inbox.deleteOldEvents();

        lastCleanTime = new Date();
    }
}
