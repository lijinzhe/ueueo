using Volo.Abp.DynamicProxy;

namespace Volo.Abp.BackgroundJobs;

/**
 * Some extension methods for <see cref="IBackgroundJobManager"/>.
*/
public static class BackgroundJobManagerExtensions
{
    /**
     * Checks if background job system has a real implementation.
     * It returns false if the current implementation is <see cref="NullBackgroundJobManager"/>.
    *
     * <param name="backgroundJobManager"></param>
     * <returns></returns>
     */
    public static boolean IsAvailable(this IBackgroundJobManager backgroundJobManager)
    {
        return !(ProxyHelper.UnProxy(backgroundJobManager) is NullBackgroundJobManager);
    }
}
