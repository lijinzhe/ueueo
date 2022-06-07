using System.Threading;
using JetBrains.Annotations;

namespace Volo.Abp.BlobStoring;

public abstract class BlobProviderArgs
{
    [NotNull]
    public String ContainerName;//  { get; }

    [NotNull]
    public BlobContainerConfiguration Configuration;//  { get; }

    [NotNull]
    public String BlobName;//  { get; }

    public CancellationToken CancellationToken;//  { get; }

    protected BlobProviderArgs(
        @NonNull String containerName,
        @NonNull BlobContainerConfiguration configuration,
        @NonNull String blobName,
        CancellationToken cancellationToken = default)
    {
        ContainerName = Check.NotNullOrWhiteSpace(containerName, nameof(containerName));
        Configuration = Objects.requireNonNull(configuration, nameof(configuration));
        BlobName = Check.NotNullOrWhiteSpace(blobName, nameof(blobName));
        CancellationToken = cancellationToken;
    }
}
