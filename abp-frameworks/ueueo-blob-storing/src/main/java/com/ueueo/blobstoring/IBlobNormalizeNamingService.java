package com.ueueo.blobstoring;

public interface IBlobNormalizeNamingService {
    BlobNormalizeNaming normalizeNaming(BlobContainerConfiguration configuration, String containerName, String blobName);

    String normalizeContainerName(BlobContainerConfiguration configuration, String containerName);

    String normalizeBlobName(BlobContainerConfiguration configuration, String blobName);
}
