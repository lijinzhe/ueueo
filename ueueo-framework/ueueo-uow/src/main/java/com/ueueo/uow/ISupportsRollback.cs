using System.Threading;
using System.Threading.Tasks;

namespace Volo.Abp.Uow;

public interface ISupportsRollback
{
    void RollbackAsync(CancellationToken cancellationToken);
}
