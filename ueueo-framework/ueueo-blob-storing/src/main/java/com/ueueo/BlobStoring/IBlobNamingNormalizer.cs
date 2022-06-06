namespace Volo.Abp.BlobStoring;

public interface IBlobNamingNormalizer
{
    String NormalizeContainerName(String containerName);

    String NormalizeBlobName(String blobName);
}
