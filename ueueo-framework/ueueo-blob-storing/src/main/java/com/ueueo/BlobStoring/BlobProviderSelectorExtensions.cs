using JetBrains.Annotations;

namespace Volo.Abp.BlobStoring;

public static class BlobProviderSelectorExtensions
{
    public static IBlobProvider Get<TContainer>(
        @NonNull this IBlobProviderSelector selector)
    {
        Objects.requireNonNull(selector, nameof(selector));

        return selector.Get(BlobContainerNameAttribute.GetContainerName<TContainer>());
    }
}
