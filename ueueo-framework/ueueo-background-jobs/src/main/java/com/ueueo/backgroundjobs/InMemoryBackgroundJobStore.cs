using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Volo.Abp.DependencyInjection;
using Volo.Abp.Timing;

namespace Volo.Abp.BackgroundJobs;

public class InMemoryBackgroundJobStore : IBackgroundJobStore, ISingletonDependency
{
    private readonly ConcurrentDictionary<Guid, BackgroundJobInfo> _jobs;

    protected IClock Clock;//  { get; }

    /**
     * Initializes a new instance of the <see cref="InMemoryBackgroundJobStore"/> class.
    */
    public InMemoryBackgroundJobStore(IClock clock)
    {
        Clock = clock;
        _jobs = new ConcurrentDictionary<Guid, BackgroundJobInfo>();
    }

    public   Task<BackgroundJobInfo> FindAsync(Guid jobId)
    {
        return Task.FromResult(_jobs.GetOrDefault(jobId));
    }

    public void InsertAsync(BackgroundJobInfo jobInfo)
    {
        _jobs[jobInfo.Id] = jobInfo;

        return Task.FromResult(0);
    }

    public   Task<List<BackgroundJobInfo>> GetWaitingJobsAsync(int maxResultCount)
    {
        var waitingJobs = _jobs.Values
            .Where(t => !t.IsAbandoned && t.NextTryTime <= Clock.Now)
            .OrderByDescending(t => t.Priority)
            .ThenBy(t => t.TryCount)
            .ThenBy(t => t.NextTryTime)
            .Take(maxResultCount)
            .ToList();

        return Task.FromResult(waitingJobs);
    }


    public void DeleteAsync(Guid jobId)
    {
        _jobs.TryRemove(jobId, out _);

        return Task.FromResult(0);
    }

    public void UpdateAsync(BackgroundJobInfo jobInfo)
    {
        if (jobInfo.IsAbandoned)
        {
            return DeleteAsync(jobInfo.Id);
        }

        return Task.FromResult(0);
    }
}
