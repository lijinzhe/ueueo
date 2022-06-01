using JetBrains.Annotations;

namespace Volo.Abp.BlobStoring;

public interface IBlobProviderSelector
{
    [NotNull]
    IBlobProvider Get(@NonNull String containerName);
}
