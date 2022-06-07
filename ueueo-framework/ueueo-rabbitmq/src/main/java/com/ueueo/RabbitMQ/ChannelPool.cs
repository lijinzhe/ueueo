using System;
using System.Collections.Concurrent;
using System.Diagnostics;
using System.Linq;
using System.Threading;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Logging.Abstractions;
using RabbitMQ.Client;
using Volo.Abp.DependencyInjection;

namespace Volo.Abp.RabbitMQ;

public class ChannelPool : IChannelPool, ISingletonDependency
{
    protected IConnectionPool ConnectionPool;//  { get; }

    protected ConcurrentDictionary<String, ChannelPoolItem> Channels;//  { get; }

    protected boolean IsDisposed ;// { get; private set; }

    protected TimeSpan TotalDisposeWaitDuration;// { get; set; } = TimeSpan.FromSeconds(10);

    public ILogger<ChannelPool> Logger;// { get; set; }

    public ChannelPool(IConnectionPool connectionPool)
    {
        ConnectionPool = connectionPool;
        Channels = new ConcurrentDictionary<String, ChannelPoolItem>();
        Logger = NullLogger<ChannelPool>.Instance;
    }

    public   IChannelAccessor Acquire(String channelName = null, String connectionName = null)
    {
        CheckDisposed();

        channelName = channelName ?? "";

        var poolItem = Channels.GetOrAdd(
            channelName,
            _ => new ChannelPoolItem(CreateChannel(channelName, connectionName))
        );

        poolItem.Acquire();

        return new ChannelAccessor(
            poolItem.Channel,
            channelName,
            () => poolItem.Release()
        );
    }

    protected   IModel CreateChannel(String channelName, String connectionName)
    {
        return ConnectionPool
            .Get(connectionName)
            .CreateModel();
    }

    protected void CheckDisposed()
    {
        if (IsDisposed)
        {
            throw new ObjectDisposedException(nameof(ChannelPool));
        }
    }

    public void Dispose()
    {
        if (IsDisposed)
        {
            return;
        }

        IsDisposed = true;

        if (!Channels.Any())
        {
            Logger.LogDebug($"Disposed channel pool with no channels in the pool.");
            return;
        }

        var poolDisposeStopwatch = Stopwatch.StartNew();

        Logger.LogInformation($"Disposing channel pool ({Channels.Count} channels).");

        var remainingWaitDuration = TotalDisposeWaitDuration;

        for (var poolItem in Channels.Values)
        {
            var poolItemDisposeStopwatch = Stopwatch.StartNew();

            try
            {
                poolItem.WaitIfInUse(remainingWaitDuration);
                poolItem.Dispose();
            }
            catch
            { }

            poolItemDisposeStopwatch.Stop();

            remainingWaitDuration = remainingWaitDuration > poolItemDisposeStopwatch.Elapsed
                ? remainingWaitDuration.Subtract(poolItemDisposeStopwatch.Elapsed)
                : TimeSpan.Zero;
        }

        poolDisposeStopwatch.Stop();

        Logger.LogInformation($"Disposed RabbitMQ Channel Pool ({Channels.Count} channels in {poolDisposeStopwatch.Elapsed.TotalMilliseconds:0.00} ms).");

        if (poolDisposeStopwatch.Elapsed.TotalSeconds > 5.0)
        {
            Logger.LogWarning($"Disposing RabbitMQ Channel Pool got time greather than expected: {poolDisposeStopwatch.Elapsed.TotalMilliseconds:0.00} ms.");
        }

        Channels.Clear();
    }

    protected class ChannelPoolItem : IDisposable
    {
        public IModel Channel;//  { get; }

        public boolean IsInUse {
            get => _isInUse;
            private set => _isInUse = value;
        }
        private volatile boolean _isInUse;

        public ChannelPoolItem(IModel channel)
        {
            Channel = channel;
        }

        public void Acquire()
        {
            lock (this)
            {
                while (IsInUse)
                {
                    Monitor.Wait(this);
                }

                IsInUse = true;
            }
        }

        public void WaitIfInUse(TimeSpan timeout)
        {
            lock (this)
            {
                if (!IsInUse)
                {
                    return;
                }

                Monitor.Wait(this, timeout);
            }
        }

        public void Release()
        {
            lock (this)
            {
                IsInUse = false;
                Monitor.PulseAll(this);
            }
        }

        public void Dispose()
        {
            Channel.Dispose();
        }
    }

    protected class ChannelAccessor : IChannelAccessor
    {
        public IModel Channel;//  { get; }

        public String Name;//  { get; }

        private readonly Action _disposeAction;

        public ChannelAccessor(IModel channel, String name, Action disposeAction)
        {
            _disposeAction = disposeAction;
            Name = name;
            Channel = channel;
        }

        public void Dispose()
        {
            _disposeAction.Invoke();
        }
    }
}
