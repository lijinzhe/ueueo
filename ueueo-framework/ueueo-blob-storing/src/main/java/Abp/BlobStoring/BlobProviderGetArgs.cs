using System.Threading;
using JetBrains.Annotations;

namespace Volo.Abp.BlobStoring;

public class BlobProviderGetArgs : BlobProviderArgs
{
    public BlobProviderGetArgs(
        @NonNull String containerName,
        @NonNull BlobContainerConfiguration configuration,
        @NonNull String blobName,
        CancellationToken cancellationToken = default)
        : base(
            containerName,
            configuration,
            blobName,
            cancellationToken)
    {
    }
}
