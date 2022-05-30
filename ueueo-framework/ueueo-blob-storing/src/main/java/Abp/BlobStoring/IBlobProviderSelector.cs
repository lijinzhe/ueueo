using JetBrains.Annotations;

namespace Volo.Abp.BlobStoring;

public interface IBlobProviderSelector
{
    [NotNull]
    IBlobProvider Get(@Nonnull String containerName);
}
