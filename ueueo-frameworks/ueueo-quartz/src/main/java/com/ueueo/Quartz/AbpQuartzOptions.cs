using System;
using System.Collections.Specialized;
using System.Threading.Tasks;
using JetBrains.Annotations;
using Quartz;

namespace Volo.Abp.Quartz;

public class AbpQuartzOptions
{
    /**
     * The quartz configuration. Available properties can be found within Quartz.Impl.StdSchedulerFactory.
    */
    public NameValueCollection Properties;// { get; set; }

    public Action<IServiceCollectionQuartzConfigurator> Configurator;// { get; set; }

    /**
     * How long Quartz should wait before starting. Default: 0.
    */
    public TimeSpan StartDelay;// { get; set; }

    [NotNull]
    public Func<IScheduler, Task> StartSchedulerFactory {
        get => _startSchedulerFactory;
        set => _startSchedulerFactory = Objects.requireNonNull(value, nameof(value));
    }
    private Func<IScheduler, Task> _startSchedulerFactory;

    public AbpQuartzOptions()
    {
        Properties = new NameValueCollection();
        StartDelay = new TimeSpan(0);
        _startSchedulerFactory = StartSchedulerAsync;
    }

    private void StartSchedulerAsync(IScheduler scheduler)
    {
        if (StartDelay.Ticks > 0)
        {
            scheduler.StartDelayed(StartDelay);
        }
        else
        {
            scheduler.Start();
        }
    }
}
