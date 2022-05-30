using System.Collections.Generic;
using System.Threading.Tasks;

namespace Volo.Abp.Uow;

public interface IUnitOfWorkEventPublisher
{
    void PublishLocalEventsAsync(IEnumerable<UnitOfWorkEventRecord> localEvents);

    void PublishDistributedEventsAsync(IEnumerable<UnitOfWorkEventRecord> distributedEvents);
}
