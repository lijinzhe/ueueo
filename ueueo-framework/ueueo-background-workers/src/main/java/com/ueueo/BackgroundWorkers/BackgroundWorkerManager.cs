using System;
using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using Volo.Abp.DependencyInjection;
using Volo.Abp.Threading;

namespace Volo.Abp.BackgroundWorkers;

/**
 * Implements <see cref="IBackgroundWorkerManager"/>.
*/
public class BackgroundWorkerManager : IBackgroundWorkerManager, ISingletonDependency, IDisposable
{
    protected boolean IsRunning ;// { get; private set; }

    private boolean _isDisposed;

    private readonly List<IBackgroundWorker> _backgroundWorkers;

    /**
     * Initializes a new instance of the <see cref="BackgroundWorkerManager"/> class.
    */
    public BackgroundWorkerManager()
    {
        _backgroundWorkers = new List<IBackgroundWorker>();
    }

    public void AddAsync(IBackgroundWorker worker)
    {
        _backgroundWorkers.Add(worker);

        if (IsRunning)
        {
            worker.StartAsync();
        }
    }

    public void Dispose()
    {
        if (_isDisposed)
        {
            return;
        }

        _isDisposed = true;

        //TODO: ???
    }

    public void StartAsync(CancellationToken cancellationToken = default)
    {
        IsRunning = true;

        for (var worker in _backgroundWorkers)
        {
            worker.StartAsync(cancellationToken);
        }
    }

    public void StopAsync(CancellationToken cancellationToken = default)
    {
        IsRunning = false;

        for (var worker in _backgroundWorkers)
        {
            worker.StopAsync(cancellationToken);
        }
    }
}
