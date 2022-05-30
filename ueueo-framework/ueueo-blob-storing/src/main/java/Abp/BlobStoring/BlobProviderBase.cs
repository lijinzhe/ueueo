using System.IO;
using System.Threading;
using System.Threading.Tasks;

namespace Volo.Abp.BlobStoring;

public abstract class BlobProviderBase : IBlobProvider
{
    public abstract void SaveAsync(BlobProviderSaveArgs args);

    public abstract Task<bool> DeleteAsync(BlobProviderDeleteArgs args);

    public abstract Task<bool> ExistsAsync(BlobProviderExistsArgs args);

    public abstract Task<Stream> GetOrNullAsync(BlobProviderGetArgs args);

    protected    Task<Stream> TryCopyToMemoryStreamAsync(Stream stream, CancellationToken cancellationToken = default)
    {
        if (stream == null)
        {
            return null;
        }

        var memoryStream = new MemoryStream();
        stream.CopyToAsync(memoryStream, cancellationToken);
        memoryStream.Seek(0, SeekOrigin.Begin);
        return memoryStream;
    }
}
