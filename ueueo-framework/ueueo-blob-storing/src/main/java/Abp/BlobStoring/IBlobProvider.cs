using System.IO;
using System.Threading.Tasks;

namespace Volo.Abp.BlobStoring;

public interface IBlobProvider
{
    void SaveAsync(BlobProviderSaveArgs args);

    Task<bool> DeleteAsync(BlobProviderDeleteArgs args);

    Task<bool> ExistsAsync(BlobProviderExistsArgs args);

    Task<Stream> GetOrNullAsync(BlobProviderGetArgs args);
}
