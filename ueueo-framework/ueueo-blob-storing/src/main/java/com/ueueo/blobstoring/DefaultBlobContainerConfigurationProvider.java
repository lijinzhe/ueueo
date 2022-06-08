package com.ueueo.blobstoring;

public class DefaultBlobContainerConfigurationProvider implements IBlobContainerConfigurationProvider {
    protected AbpBlobStoringOptions options;

    public DefaultBlobContainerConfigurationProvider(AbpBlobStoringOptions options) {
        this.options = options;
    }

    @Override
    public BlobContainerConfiguration get(String name) {
        return options.getContainers().getConfiguration(name);
    }

    public AbpBlobStoringOptions getOptions() {
        return options;
    }
}
