using System.Threading;
using System.Threading.Tasks;

namespace Volo.Abp.Threading;

/**
 * Interface to start/stop self threaded services.
*/
public interface IRunnable
{
    /**
     * Starts the service.
    */
    void StartAsync(CancellationToken cancellationToken = default);

    /**
     * Stops the service.
    */
    void StopAsync(CancellationToken cancellationToken = default);
}
