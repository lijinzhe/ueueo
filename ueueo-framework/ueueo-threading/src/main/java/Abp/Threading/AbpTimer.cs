using System;
using System.Threading;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Logging.Abstractions;
using Volo.Abp.DependencyInjection;
using Volo.Abp.ExceptionHandling;

namespace Volo.Abp.Threading;

/**
 * A robust timer implementation that ensures no overlapping occurs. It waits exactly specified <see cref="Period"/> between ticks.
*/
public class AbpTimer : ITransientDependency
{
    /**
     * This event is raised periodically according to Period of Timer.
    */
    public event EventHandler Elapsed;

    /**
     * void period of timer (as milliseconds).
    */
    public int Period;// { get; set; }

    /**
     * Indicates whether timer raises Elapsed event on Start method of Timer for once.
     * Default: False.
    */
    public boolean RunOnStart;// { get; set; }

    public ILogger<AbpTimer> Logger;// { get; set; }

    public IExceptionNotifier ExceptionNotifier;// { get; set; }

    private readonly Timer _taskTimer;
    private volatile boolean _performingTasks;
    private volatile boolean _isRunning;

    public AbpTimer()
    {
        ExceptionNotifier = NullExceptionNotifier.Instance;
        Logger = NullLogger<AbpTimer>.Instance;

        _taskTimer = new Timer(
            TimerCallBack,
            null,
            Timeout.Infinite,
            Timeout.Infinite
        );
    }

    public void Start(CancellationToken cancellationToken = default)
    {
        if (Period <= 0)
        {
            throw new AbpException("Period should be set before starting the timer!");
        }

        lock (_taskTimer)
        {
            _taskTimer.Change(RunOnStart ? 0 : Period, Timeout.Infinite);
            _isRunning = true;
        }
    }

    public void Stop(CancellationToken cancellationToken = default)
    {
        lock (_taskTimer)
        {
            _taskTimer.Change(Timeout.Infinite, Timeout.Infinite);
            while (_performingTasks)
            {
                Monitor.Wait(_taskTimer);
            }

            _isRunning = false;
        }
    }

    /**
     * This method is called by _taskTimer.
    *
     * <param name="state">Not used argument</param>
     */
    private void TimerCallBack(Object state)
    {
        lock (_taskTimer)
        {
            if (!_isRunning || _performingTasks)
            {
                return;
            }

            _taskTimer.Change(Timeout.Infinite, Timeout.Infinite);
            _performingTasks = true;
        }

        try
        {
            Elapsed.InvokeSafely(this, EventArgs.Empty);
        }
        catch (Exception ex)
        {
            Logger.LogException(ex);
            AsyncHelper.RunSync(() => ExceptionNotifier.NotifyAsync(ex));
        }
        finally
        {
            lock (_taskTimer)
            {
                _performingTasks = false;
                if (_isRunning)
                {
                    _taskTimer.Change(Period, Timeout.Infinite);
                }

                Monitor.Pulse(_taskTimer);
            }
        }
    }
}
