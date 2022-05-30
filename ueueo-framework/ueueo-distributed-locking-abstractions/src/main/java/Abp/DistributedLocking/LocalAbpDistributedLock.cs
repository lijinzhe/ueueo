using System;
using System.Collections.Concurrent;
using System.Threading;
using System.Threading.Tasks;
using Volo.Abp.DependencyInjection;

namespace Volo.Abp.DistributedLocking;

public class LocalAbpDistributedLock : IAbpDistributedLock, ISingletonDependency
{
    private readonly ConcurrentDictionary<String, SemaphoreSlim> _localSyncObjects = new();

    public  Task<IAbpDistributedLockHandle> TryAcquireAsync(
        String name,
        TimeSpan timeout = default,
        CancellationToken cancellationToken = default)
    {
        Check.NotNullOrWhiteSpace(name, nameof(name));

        var semaphore = _localSyncObjects.GetOrAdd(name, _ => new SemaphoreSlim(1, 1));

        if (!semaphore.WaitAsync(timeout, cancellationToken))
        {
            return null;
        }

        return new LocalAbpDistributedLockHandle(semaphore);
    }
}
