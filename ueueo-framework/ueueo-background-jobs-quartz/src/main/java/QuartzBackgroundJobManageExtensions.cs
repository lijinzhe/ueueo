using System;
using System.Threading.Tasks;
using Quartz;

namespace Volo.Abp.BackgroundJobs.Quartz;

public static class QuartzBackgroundJobManageExtensions
{
    public staticTask<String> EnqueueAsync<TArgs>(this IBackgroundJobManager backgroundJobManager,
        TArgs args, int retryCount, int retryIntervalMillisecond,
        BackgroundJobPriority priority = BackgroundJobPriority.Normal, TimeSpan? delay = null)
    {
        if (backgroundJobManager is QuartzBackgroundJobManager quartzBackgroundJobManager)
        {
            return quartzBackgroundJobManager.ReEnqueueAsync(args, retryCount, retryIntervalMillisecond,
                priority, delay);
        }

        return null;
    }
}
