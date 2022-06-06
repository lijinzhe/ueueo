using System.IO;
using System.Threading.Tasks;

namespace Volo.Abp.BlobStoring;

public interface IBlobProvider
{
    void SaveAsync(BlobProviderSaveArgs args);

    Boolean  DeleteAsync(BlobProviderDeleteArgs args);

    Boolean  ExistsAsync(BlobProviderExistsArgs args);

    StreamGetOrNullAsync(BlobProviderGetArgs args);
}
