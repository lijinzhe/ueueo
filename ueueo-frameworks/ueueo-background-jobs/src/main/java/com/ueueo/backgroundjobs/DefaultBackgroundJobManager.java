package com.ueueo.backgroundjobs;

import com.ueueo.ID;
import com.ueueo.guids.IGuidGenerator;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Default implementation of <see cref="IBackgroundJobManager"/>.
 */
public class DefaultBackgroundJobManager implements IBackgroundJobManager {
    protected IBackgroundJobSerializer backgroundJobSerializer;
    protected IGuidGenerator guidGenerator;
    protected IBackgroundJobStore backgroundJobStore;

    public DefaultBackgroundJobManager(
            IBackgroundJobSerializer backgroundJobSerializer,
            IBackgroundJobStore backgroundJobStore,
            IGuidGenerator guidGenerator) {
        this.backgroundJobSerializer = backgroundJobSerializer;
        this.guidGenerator = guidGenerator;
        this.backgroundJobStore = backgroundJobStore;
    }

    @Override
    public ID enqueue(String jobName, Object args, BackgroundJobPriority priority, Integer delay, TimeUnit delayTimeUnit) {

        BackgroundJobInfo jobInfo = new BackgroundJobInfo();
        jobInfo.setId(guidGenerator.create());
        jobInfo.setJobName(jobName);
        jobInfo.setJobArgs(backgroundJobSerializer.serialize(args));
        jobInfo.setCreationTime(new Date());
        jobInfo.setNextTryTime(new Date());
        jobInfo.setPriority(priority);

        if (delay != null && delay > 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MILLISECOND, (int) delayTimeUnit.toMillis(delay));
            jobInfo.setNextTryTime(calendar.getTime());
        }

        backgroundJobStore.insert(jobInfo);

        return jobInfo.getId();
    }

}
