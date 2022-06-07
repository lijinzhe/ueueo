namespace Volo.Abp.BlobStoring;

public interface IBlobNormalizeNamingService
{
    BlobNormalizeNaming NormalizeNaming(BlobContainerConfiguration configuration, String containerName, String blobName);

    String NormalizeContainerName(BlobContainerConfiguration configuration, String containerName);

    String NormalizeBlobName(BlobContainerConfiguration configuration, String blobName);
}
