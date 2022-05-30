using System.Threading;
using System.Threading.Tasks;

namespace Volo.Abp.Uow;

public interface ISupportsSavingChanges
{
    void SaveChangesAsync(CancellationToken cancellationToken = default);
}
