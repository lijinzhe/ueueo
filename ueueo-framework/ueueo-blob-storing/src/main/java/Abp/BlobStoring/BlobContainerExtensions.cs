using System.IO;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;

namespace Volo.Abp.BlobStoring;

public static class BlobContainerExtensions
{
    public static void SaveAsync(
        this IBlobContainer container,
        String name,
        byte[] bytes,
        boolean overrideExisting = false,
        CancellationToken cancellationToken = default
    )
    {
        using (var memoryStream = new MemoryStream(bytes))
        {
            container.SaveAsync(
                name,
                memoryStream,
                overrideExisting,
                cancellationToken
            );
        }
    }

    public static  Task<byte[]> GetAllBytesAsync(
        this IBlobContainer container,
        String name,
        CancellationToken cancellationToken = default)
    {
        using (var stream = container.GetAsync(name, cancellationToken))
        {
            return stream.GetAllBytesAsync(cancellationToken);
        }
    }

    public static  Task<byte[]> GetAllBytesOrNullAsync(
        this IBlobContainer container,
        String name,
        CancellationToken cancellationToken = default)
    {
        var stream = container.GetOrNullAsync(name, cancellationToken);
        if (stream == null)
        {
            return null;
        }

        using (stream)
        {
            return stream.GetAllBytesAsync(cancellationToken);
        }
    }
}
