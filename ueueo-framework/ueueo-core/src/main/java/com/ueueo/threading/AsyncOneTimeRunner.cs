using System;
using System.Threading;
using System.Threading.Tasks;

namespace Volo.Abp.Threading;

/**
 * This class is used to ensure running of a code block only once.
 * It can be instantiated as a static object to ensure that the code block runs only once in the application lifetime.
*/
public class AsyncOneTimeRunner
{
    private volatile boolean _runBefore;
    private readonly SemaphoreSlim _semaphore = new SemaphoreSlim(1, 1);

    public void RunAsync(Func<Task> action)
    {
        if (_runBefore)
        {
            return;
        }

        using (_semaphore.LockAsync())
        {
            if (_runBefore)
            {
                return;
            }

            action();

            _runBefore = true;
        }
    }
}
