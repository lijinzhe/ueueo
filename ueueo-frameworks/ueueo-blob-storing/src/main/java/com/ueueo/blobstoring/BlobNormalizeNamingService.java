package com.ueueo.blobstoring;

import org.apache.commons.lang3.StringUtils;

public class BlobNormalizeNamingService implements IBlobNormalizeNamingService {

    public BlobNormalizeNamingService() {
    }

    @Override
    public BlobNormalizeNaming normalizeNaming(
            BlobContainerConfiguration configuration,
            String containerName,
            String blobName) {

        if (configuration.getNamingNormalizers().isEmpty()) {
            return new BlobNormalizeNaming(containerName, blobName);
        }

        for (IBlobNamingNormalizer normalizer : configuration.getNamingNormalizers()) {
            containerName = StringUtils.isBlank(containerName) ? containerName : normalizer.normalizeContainerName(containerName);
            blobName = StringUtils.isBlank(blobName) ? blobName : normalizer.normalizeBlobName(blobName);
        }

        return new BlobNormalizeNaming(containerName, blobName);

    }

    @Override
    public String normalizeContainerName(BlobContainerConfiguration configuration, String containerName) {
        if (configuration.getNamingNormalizers().isEmpty()) {
            return containerName;
        }

        return normalizeNaming(configuration, containerName, null).getContainerName();
    }

    @Override
    public String normalizeBlobName(BlobContainerConfiguration configuration, String blobName) {
        if (configuration.getNamingNormalizers().isEmpty()) {
            return blobName;
        }

        return normalizeNaming(configuration, null, blobName).getBlobName();
    }
}
