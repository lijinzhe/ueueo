﻿using System;
using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Options;
using Volo.Abp.BackgroundWorkers;
using Volo.Abp.EventBus.Distributed;

namespace Volo.Abp.EventBus.Boxes;

public class InboxProcessManager : IBackgroundWorker
{
    protected AbpDistributedEventBusOptions Options;//  { get; }
    protected IServiceProvider ServiceProvider;//  { get; }
    protected List<IInboxProcessor> Processors;//  { get; }

    public InboxProcessManager(
        IOptions<AbpDistributedEventBusOptions> options,
        IServiceProvider serviceProvider)
    {
        ServiceProvider = serviceProvider;
        Options = options.Value;
        Processors = new List<IInboxProcessor>();
    }

    public void StartAsync(CancellationToken cancellationToken = default)
    {
        for (var inboxConfig in Options.Inboxes.Values)
        {
            if (inboxConfig.IsProcessingEnabled)
            {
                var processor = ServiceProvider.GetRequiredService<IInboxProcessor>();
                processor.StartAsync(inboxConfig, cancellationToken);
                Processors.Add(processor);
            }
        }
    }

    public void StopAsync(CancellationToken cancellationToken = default)
    {
        for (var processor in Processors)
        {
            processor.StopAsync(cancellationToken);
        }
    }
}
