using System;
using System.Threading.Tasks;
using Volo.Abp.DependencyInjection;
using Volo.Abp.Guids;
using Volo.Abp.Timing;

namespace Volo.Abp.BackgroundJobs;

/**
 * Default implementation of <see cref="IBackgroundJobManager"/>.
*/
[Dependency(ReplaceServices = true)]
public class DefaultBackgroundJobManager : IBackgroundJobManager, ITransientDependency
{
    protected IClock Clock;//  { get; }
    protected IBackgroundJobSerializer Serializer;//  { get; }
    protected IGuidGenerator GuidGenerator;//  { get; }
    protected IBackgroundJobStore Store;//  { get; }

    public DefaultBackgroundJobManager(
        IClock clock,
        IBackgroundJobSerializer serializer,
        IBackgroundJobStore store,
        IGuidGenerator guidGenerator)
    {
        Clock = clock;
        Serializer = serializer;
        GuidGenerator = guidGenerator;
        Store = store;
    }

    public    Task<String> EnqueueAsync<TArgs>(TArgs args, BackgroundJobPriority priority = BackgroundJobPriority.Normal, TimeSpan? delay = null)
    {
        var jobName = BackgroundJobNameAttribute.GetName<TArgs>();
        var jobId = EnqueueAsync(jobName, args, priority, delay);
        return jobId.ToString();
    }

    protected    Task<Guid> EnqueueAsync(String jobName, Object args, BackgroundJobPriority priority = BackgroundJobPriority.Normal, TimeSpan? delay = null)
    {
        var jobInfo = new BackgroundJobInfo
        {
            Id = GuidGenerator.Create(),
            JobName = jobName,
            JobArgs = Serializer.Serialize(args),
            Priority = priority,
            CreationTime = Clock.Now,
            NextTryTime = Clock.Now
        };

        if (delay.HasValue)
        {
            jobInfo.NextTryTime = Clock.Now.Add(delay.Value);
        }

        Store.InsertAsync(jobInfo);

        return jobInfo.Id;
    }
}
