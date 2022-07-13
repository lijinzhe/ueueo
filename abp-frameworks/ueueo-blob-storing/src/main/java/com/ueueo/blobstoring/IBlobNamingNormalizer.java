package com.ueueo.blobstoring;

public interface IBlobNamingNormalizer {
    String normalizeContainerName(String containerName);

    String normalizeBlobName(String blobName);
}
