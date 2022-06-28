package com.ueueo.backgroundjobs;

import com.ueueo.backgroundworkers.AsyncPeriodicBackgroundWorkerBase;
import com.ueueo.backgroundworkers.PeriodicBackgroundWorkerContext;
import com.ueueo.distributedlocking.IDistributedLock;
import com.ueueo.distributedlocking.IDistributedLockHandle;
import com.ueueo.threading.CancellationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;

import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class BackgroundJobWorker extends AsyncPeriodicBackgroundWorkerBase implements IBackgroundJobWorker {
    protected final String DistributedLockName = "AbpBackgroundJobWorker";

    protected AbpBackgroundJobOptions jobOptions;

    protected AbpBackgroundJobWorkerOptions workerOptions;

    protected IDistributedLock distributedLock;

    private IBackgroundJobStore backgroundJobStore;
    private IBackgroundJobExecuter backgroundJobExecuter;
    private IBackgroundJobSerializer backgroundJobSerializer;

    public BackgroundJobWorker(
            AbpBackgroundJobOptions jobOptions,
            AbpBackgroundJobWorkerOptions workerOptions,
            BeanFactory beanFactory,
            IDistributedLock distributedLock,
            IBackgroundJobStore backgroundJobStore,
            IBackgroundJobExecuter backgroundJobExecuter,
            IBackgroundJobSerializer backgroundJobSerializer) {
        super(workerOptions.getDefaultFirstWaitDuration(), workerOptions.getJobPollPeriod(), TimeUnit.MILLISECONDS, beanFactory);
        this.distributedLock = distributedLock;
        this.workerOptions = workerOptions;
        this.jobOptions = jobOptions;
        this.backgroundJobStore = backgroundJobStore;
        this.backgroundJobExecuter = backgroundJobExecuter;
        this.backgroundJobSerializer = backgroundJobSerializer;
    }

    @Override
    protected void doWork(PeriodicBackgroundWorkerContext workerContext, CancellationToken cancellationToken) {
        IDistributedLockHandle lock = distributedLock.tryAcquire(DistributedLockName, Duration.ofSeconds(30));
        if (lock != null) {

            List<BackgroundJobInfo> waitingJobs = backgroundJobStore.getWaitingJobs(workerOptions.getMaxJobFetchCount());

            if (waitingJobs.isEmpty()) {
                return;
            }

            for (BackgroundJobInfo jobInfo : waitingJobs) {
                jobInfo.setTryCount(jobInfo.getTryCount() + 1);
                jobInfo.setLastTryTime(new Date());

                try {
                    BackgroundJobConfiguration jobConfiguration = jobOptions.getJob(jobInfo.getJobName());
                    Object jobArgs = backgroundJobSerializer.deserialize(jobInfo.getJobArgs(), Object.class);
                    JobExecutionContext context = new JobExecutionContext(
                            workerContext.getBeanFactory(),
                            jobConfiguration.getJobType(),
                            jobArgs);

                    try {
                        backgroundJobExecuter.execute(context);

                        backgroundJobStore.delete(jobInfo.getId());
                    } catch (BackgroundJobExecutionException e) {
                        Date nextTryTime = calculateNextTryTime(jobInfo);

                        if (nextTryTime != null) {
                            jobInfo.setNextTryTime(nextTryTime);
                        } else {
                            jobInfo.setAbandoned(true);
                        }

                        tryUpdateAsync(backgroundJobStore, jobInfo);
                    }
                } catch (Exception ex) {
                    log.info(ex.getMessage(), ex);
                    jobInfo.setAbandoned(true);
                    tryUpdateAsync(backgroundJobStore, jobInfo);
                }
            }
            lock.dispose();
        } else {
            try {
                Thread.sleep(workerOptions.getJobPollPeriod() * 12L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected void tryUpdateAsync(IBackgroundJobStore store, BackgroundJobInfo jobInfo) {
        try {
            store.update(jobInfo);
        } catch (Exception updateEx) {
            log.info(updateEx.getMessage(), updateEx);
        }
    }

    protected Date calculateNextTryTime(BackgroundJobInfo jobInfo) {
        int nextWaitDuration = (int) (workerOptions.getDefaultFirstWaitDuration() *
                (Math.pow(workerOptions.getDefaultWaitFactor(), jobInfo.getTryCount() - 1)));

        Calendar calendar = Calendar.getInstance();
        if (jobInfo.getLastTryTime() != null) {
            calendar.setTime(jobInfo.getLastTryTime());
        } else {
            calendar.setTime(new Date());
        }
        calendar.add(Calendar.SECOND, nextWaitDuration);
        Date nextTryDate = calendar.getTime();

        if (nextTryDate.getTime() - jobInfo.getCreationTime().getTime() > workerOptions.getDefaultTimeout() * 1000L) {
            return null;
        }

        return nextTryDate;
    }
}
