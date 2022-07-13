package com.ueueo.blobstoring;

public class AbpBlobStoringOptions {
    private BlobContainerConfigurations containers;

    public AbpBlobStoringOptions() {
        containers = new BlobContainerConfigurations();
    }

    public BlobContainerConfigurations getContainers() {
        return containers;
    }
}
