namespace Volo.Abp.BlobStoring;

public class BlobNormalizeNaming
{
    public String ContainerName;//  { get; }

    public String BlobName;//  { get; }

    public BlobNormalizeNaming(String containerName, String blobName)
    {
        ContainerName = containerName;
        BlobName = blobName;
    }
}
