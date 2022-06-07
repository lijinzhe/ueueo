using System;
using System.Threading.Tasks;
using Quartz;

namespace Volo.Abp.BackgroundWorkers.Quartz;

public abstract class QuartzBackgroundWorkerBase : BackgroundWorkerBase, IQuartzBackgroundWorker
{
    public ITrigger Trigger;// { get; set; }

    public IJobDetail JobDetail;// { get; set; }

    public boolean AutoRegister;// { get; set; } = true;

    public Func<IScheduler, Task> ScheduleJob;// { get; set; } = null;

    public abstract void Execute(IJobExecutionContext context);
}
