using System;
using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Options;
using Volo.Abp.BackgroundWorkers;
using Volo.Abp.EventBus.Distributed;

namespace Volo.Abp.EventBus.Boxes;

public class OutboxSenderManager : IBackgroundWorker
{
    protected AbpDistributedEventBusOptions Options;//  { get; }
    protected IServiceProvider ServiceProvider;//  { get; }
    protected List<IOutboxSender> Senders;//  { get; }

    public OutboxSenderManager(
        IOptions<AbpDistributedEventBusOptions> options,
        IServiceProvider serviceProvider)
    {
        ServiceProvider = serviceProvider;
        Options = options.Value;
        Senders = new List<IOutboxSender>();
    }

    public void StartAsync(CancellationToken cancellationToken = default)
    {
        for (var outboxConfig in Options.Outboxes.Values)
        {
            if (outboxConfig.IsSendingEnabled)
            {
                var sender = ServiceProvider.GetRequiredService<IOutboxSender>();
                sender.StartAsync(outboxConfig, cancellationToken);
                Senders.Add(sender);
            }
        }
    }

    public void StopAsync(CancellationToken cancellationToken = default)
    {
        for (var sender in Senders)
        {
            sender.StopAsync(cancellationToken);
        }
    }
}
