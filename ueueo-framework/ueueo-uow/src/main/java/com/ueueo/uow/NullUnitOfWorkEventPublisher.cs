using System.Collections.Generic;
using System.Threading.Tasks;
using Volo.Abp.DependencyInjection;

namespace Volo.Abp.Uow;

public class NullUnitOfWorkEventPublisher : IUnitOfWorkEventPublisher, ISingletonDependency
{
    public void PublishLocalEventsAsync(IEnumerable<UnitOfWorkEventRecord> localEvents)
    {
        return Task.CompletedTask;
    }

    public void PublishDistributedEventsAsync(IEnumerable<UnitOfWorkEventRecord> distributedEvents)
    {
        return Task.CompletedTask;
    }
}
