package com.ueueo.backgroundjobs;

import com.ueueo.ID;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryBackgroundJobStore implements IBackgroundJobStore {
    private ConcurrentHashMap<ID, BackgroundJobInfo> jobs;

    /**
     * Initializes a new instance of the <see cref="InMemoryBackgroundJobStore"/> class.
     */
    public InMemoryBackgroundJobStore() {
        jobs = new ConcurrentHashMap<>();
    }

    @Override
    public BackgroundJobInfo find(ID jobId) {
        return jobs.get(jobId);
    }

    @Override
    public void insert(BackgroundJobInfo jobInfo) {
        jobs.put(jobInfo.getId(), jobInfo);
    }

    @Override
    public List<BackgroundJobInfo> getWaitingJobs(int maxResultCount) {
        List<BackgroundJobInfo> waitingJobs = jobs.values().stream()
                .filter(t -> !t.isAbandoned() && t.getNextTryTime().before(new Date()))
                .sorted(new Comparator<BackgroundJobInfo>() {
                    @Override
                    public int compare(BackgroundJobInfo job1, BackgroundJobInfo job2) {
                        if (job1.getPriority().getIntValue() > job2.getPriority().getIntValue()) {
                            return -1;
                        } else if (job1.getPriority().getIntValue() < job2.getPriority().getIntValue()) {
                            return 1;
                        }
                        if (job1.getTryCount() > job2.getTryCount()) {
                            return -1;
                        } else if (job1.getTryCount() < job2.getTryCount()) {
                            return 1;
                        }
                        if (job1.getNextTryTime().after(job2.getNextTryTime())) {
                            return -1;
                        } else if (job1.getNextTryTime().before(job2.getNextTryTime())) {
                            return 1;
                        }
                        return 0;
                    }
                })
                .limit(maxResultCount)
                .collect(Collectors.toList());

        //        List<BackgroundJobInfo> waitingJobs = _jobs.Values
        //            .Where(t => !t.IsAbandoned && t.NextTryTime <= Clock.Now)
        //            .OrderByDescending(t => t.Priority)
        //            .ThenBy(t => t.TryCount)
        //            .ThenBy(t => t.NextTryTime)
        //            .Take(maxResultCount)
        //            .ToList();

        return waitingJobs;
    }

    @Override
    public void delete(ID jobId) {
        jobs.remove(jobId);

    }

    @Override
    public void update(BackgroundJobInfo jobInfo) {
        if (jobInfo.isAbandoned()) {
            delete(jobInfo.getId());
        }

    }
}
