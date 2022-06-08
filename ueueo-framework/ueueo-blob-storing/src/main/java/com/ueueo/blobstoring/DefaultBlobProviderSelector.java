package com.ueueo.blobstoring;

import com.ueueo.AbpException;
import com.ueueo.dynamicproxy.ProxyHelper;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Objects;

public class DefaultBlobProviderSelector implements IBlobProviderSelector {

    protected List<IBlobProvider> blobProviders;

    protected IBlobContainerConfigurationProvider configurationProvider;

    public DefaultBlobProviderSelector(
            IBlobContainerConfigurationProvider configurationProvider,
            List<IBlobProvider> blobProviders) {
        this.configurationProvider = configurationProvider;
        this.blobProviders = blobProviders;
    }

    @NonNull
    @Override
    public IBlobProvider get(@NonNull String containerName) {
        Objects.requireNonNull(containerName);

        BlobContainerConfiguration configuration = configurationProvider.get(containerName);

        if (blobProviders.isEmpty()) {
            throw new AbpException("No BLOB Storage provider was registered! At least one provider must be registered to be able to use the BLOB Storing System.");
        }

        if (configuration.getProviderType() == null) {
            throw new AbpException("No BLOB Storage provider was used! At least one provider must be configured to be able to use the BLOB Storing System.");
        }

        for (IBlobProvider provider : blobProviders) {
            if (configuration.getProviderType().isAssignableFrom(ProxyHelper.getUnProxiedType(provider))) {
                return provider;
            }
        }

        throw new AbpException(
                String.format("Could not find the BLOB Storage provider with the type (%s) configured for the container %s and no default provider was set.",
                        configuration.getProviderType().getName(), containerName)
        );
    }

    public List<IBlobProvider> getBlobProviders() {
        return blobProviders;
    }

    public IBlobContainerConfigurationProvider getConfigurationProvider() {
        return configurationProvider;
    }
}
