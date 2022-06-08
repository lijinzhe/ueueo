package com.ueueo.blobstoring;

import com.ueueo.threading.ICancellationTokenProvider;

public class BlobContainerFactory implements IBlobContainerFactory {
    protected IBlobProviderSelector providerSelector;

    protected IBlobContainerConfigurationProvider configurationProvider;

    protected ICancellationTokenProvider cancellationTokenProvider;

    protected IBlobNormalizeNamingService blobNormalizeNamingService;

    public BlobContainerFactory(
            IBlobContainerConfigurationProvider configurationProvider,
            ICancellationTokenProvider cancellationTokenProvider,
            IBlobProviderSelector providerSelector,
            IBlobNormalizeNamingService blobNormalizeNamingService) {
        this.configurationProvider = configurationProvider;
        this.cancellationTokenProvider = cancellationTokenProvider;
        this.providerSelector = providerSelector;
        this.blobNormalizeNamingService = blobNormalizeNamingService;
    }

    @Override
    public IBlobContainer create(String name) {
        BlobContainerConfiguration configuration = configurationProvider.get(name);

        return new BlobContainer(
                name,
                configuration,
                providerSelector.get(name),
                cancellationTokenProvider,
                blobNormalizeNamingService
        );
    }

    /**
     * Gets a named container.
     *
     * <param name="blobContainerFactory">The blob container manager</param>
     * <returns>
     * The container object.
     * </returns>
     */
    public IBlobContainer create(Class<?> containerType) {
        BlobContainerNameAttribute attribute = containerType.getAnnotation(BlobContainerNameAttribute.class);
        return create(attribute.name());
    }

    public IBlobProviderSelector getProviderSelector() {
        return providerSelector;
    }

    public IBlobContainerConfigurationProvider getConfigurationProvider() {
        return configurationProvider;
    }

    public ICancellationTokenProvider getCancellationTokenProvider() {
        return cancellationTokenProvider;
    }

    public IBlobNormalizeNamingService getBlobNormalizeNamingService() {
        return blobNormalizeNamingService;
    }
}
