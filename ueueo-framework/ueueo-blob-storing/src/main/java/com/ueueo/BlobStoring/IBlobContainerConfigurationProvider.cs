namespace Volo.Abp.BlobStoring;

public interface IBlobContainerConfigurationProvider
{
    /**
     * Gets a <see cref="BlobContainerConfiguration"/> for the given container <paramref name="name"/>.
    *
     * <param name="name">The name of the container</param>
     * <returns>The configuration that should be used for the container</returns>
     */
    BlobContainerConfiguration Get(String name);
}
