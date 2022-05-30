using System.Threading;
using JetBrains.Annotations;

namespace Volo.Abp.BlobStoring;

public class BlobProviderGetArgs : BlobProviderArgs
{
    public BlobProviderGetArgs(
        @Nonnull String containerName,
        @Nonnull BlobContainerConfiguration configuration,
        @Nonnull String blobName,
        CancellationToken cancellationToken = default)
        : base(
            containerName,
            configuration,
            blobName,
            cancellationToken)
    {
    }
}
