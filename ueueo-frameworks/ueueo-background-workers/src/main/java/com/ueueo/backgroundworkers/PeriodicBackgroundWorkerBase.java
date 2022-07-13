package com.ueueo.backgroundworkers;

import com.ueueo.exceptionhandling.ExceptionNotificationContext;
import com.ueueo.exceptionhandling.IExceptionNotifier;
import com.ueueo.threading.CancellationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Extends <see cref="BackgroundWorkerBase"/> to add a periodic running Timer.
 */
@Slf4j
public abstract class PeriodicBackgroundWorkerBase extends BackgroundWorkerBase {
    protected BeanFactory beanFactory;
    private ScheduledExecutorService executorService;
    private long initialDelay;
    private long period;
    private TimeUnit unit;
    private CancellationToken cancellationToken;

    protected PeriodicBackgroundWorkerBase(
            long initialDelay,
            long period,
            TimeUnit unit,
            BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        this.initialDelay = initialDelay;
        this.period = period;
        this.unit = unit;
        executorService = new ScheduledThreadPoolExecutor(1);
        cancellationToken = new CancellationToken();
    }

    @Override
    public void start() {
        super.start();
        if (cancellationToken.isCanceled()) {
            cancellationToken = new CancellationToken();
        }
        executorService.scheduleAtFixedRate(this::schedule, initialDelay, period, unit);
    }

    @Override
    public void stop() {
        cancellationToken.cancel();
        executorService.shutdown();
        super.stop();
    }

    private void schedule() {
        try {
            doWork(new PeriodicBackgroundWorkerContext(beanFactory), cancellationToken);
        } catch (Exception ex) {
            IExceptionNotifier exceptionNotifier = beanFactory.getBean(IExceptionNotifier.class);
            exceptionNotifier.notify(new ExceptionNotificationContext(ex, null, false));
            log.error("Timer Elapsed Error", ex);
        }
    }

    /**
     * Periodic works should be done by implementing this method.
     */
    protected abstract void doWork(PeriodicBackgroundWorkerContext workerContext, CancellationToken cancellationToken);
}
