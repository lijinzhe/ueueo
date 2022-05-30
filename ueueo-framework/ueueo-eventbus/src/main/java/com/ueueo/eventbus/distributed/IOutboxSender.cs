using System.Threading;
using System.Threading.Tasks;
using Volo.Abp.EventBus.Distributed;

namespace Volo.Abp.EventBus.Boxes;

public interface IOutboxSender
{
    void StartAsync(OutboxConfig outboxConfig, CancellationToken cancellationToken = default);

    void StopAsync(CancellationToken cancellationToken = default);
}
