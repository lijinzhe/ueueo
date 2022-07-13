package com.ueueo.blobstoring;

public class BlobNormalizeNaming {
    public String containerName;

    public String blobName;

    public BlobNormalizeNaming(String containerName, String blobName) {
        this.containerName = containerName;
        this.blobName = blobName;
    }

    public String getContainerName() {
        return containerName;
    }

    public String getBlobName() {
        return blobName;
    }
}
