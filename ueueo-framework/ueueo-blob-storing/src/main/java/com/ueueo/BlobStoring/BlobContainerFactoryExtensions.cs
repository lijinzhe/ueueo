namespace Volo.Abp.BlobStoring;

public static class BlobContainerFactoryExtensions
{
    /**
     * Gets a named container.
     *
     * <param name="blobContainerFactory">The blob container manager</param>
     * <returns>
     * The container object.
     * </returns>
     */
    public static IBlobContainer Create<TContainer>(
        this IBlobContainerFactory blobContainerFactory
    )
    {
        return blobContainerFactory.Create(
            BlobContainerNameAttribute.GetContainerName<TContainer>()
        );
    }
}
