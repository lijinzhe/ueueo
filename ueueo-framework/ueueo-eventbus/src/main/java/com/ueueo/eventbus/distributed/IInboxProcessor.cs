using System.Threading;
using System.Threading.Tasks;
using Volo.Abp.EventBus.Distributed;

namespace Volo.Abp.EventBus.Boxes;

public interface IInboxProcessor
{
    void StartAsync(InboxConfig inboxConfig, CancellationToken cancellationToken = default);

    void StopAsync(CancellationToken cancellationToken = default);
}
