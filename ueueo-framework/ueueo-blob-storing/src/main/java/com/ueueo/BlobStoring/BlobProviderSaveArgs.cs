using System.IO;
using System.Threading;
using JetBrains.Annotations;

namespace Volo.Abp.BlobStoring;

public class BlobProviderSaveArgs : BlobProviderArgs
{
    [NotNull]
    public Stream BlobStream;//  { get; }

    public boolean OverrideExisting;//  { get; }

    public BlobProviderSaveArgs(
        @NonNull String containerName,
        @NonNull BlobContainerConfiguration configuration,
        @NonNull String blobName,
        @NonNull Stream blobStream,
        boolean overrideExisting = false,
        CancellationToken cancellationToken = default)
        : base(
            containerName,
            configuration,
            blobName,
            cancellationToken)
    {
        BlobStream = Objects.requireNonNull(blobStream, nameof(blobStream));
        OverrideExisting = overrideExisting;
    }
}
