package com.ueueo.blobstoring;

import com.ueueo.SystemException;
import com.ueueo.threading.CancellationToken;
import com.ueueo.threading.ICancellationTokenProvider;

import java.io.InputStream;

public class BlobContainer implements IBlobContainer {
    protected String containerName;

    protected BlobContainerConfiguration configuration;

    protected IBlobProvider provider;

    protected ICancellationTokenProvider cancellationTokenProvider;

    protected IBlobNormalizeNamingService blobNormalizeNamingService;

    public BlobContainer(
            String containerName,
            BlobContainerConfiguration configuration,
            IBlobProvider provider,
            ICancellationTokenProvider cancellationTokenProvider,
            IBlobNormalizeNamingService blobNormalizeNamingService
    ) {
        this.containerName = containerName;
        this.configuration = configuration;
        this.provider = provider;
        this.cancellationTokenProvider = cancellationTokenProvider;
        this.blobNormalizeNamingService = blobNormalizeNamingService;

    }

    @Override
    public void save(String name, InputStream stream, boolean overrideExisting, CancellationToken cancellationToken) {
        BlobNormalizeNaming blobNormalizeNaming = blobNormalizeNamingService.normalizeNaming(configuration, containerName, name);

        provider.save(
                new BlobProviderSaveArgs(
                        blobNormalizeNaming.getContainerName(),
                        configuration,
                        blobNormalizeNaming.getBlobName(),
                        stream,
                        overrideExisting,
                        ICancellationTokenProvider.Extensions.fallbackToProvider(cancellationTokenProvider, cancellationToken)
                )
        );
    }

    @Override
    public Boolean delete(String name, CancellationToken cancellationToken) {
        BlobNormalizeNaming blobNormalizeNaming = blobNormalizeNamingService.normalizeNaming(configuration, containerName, name);

        return provider.delete(
                new BlobProviderDeleteArgs(
                        blobNormalizeNaming.getContainerName(),
                        configuration,
                        blobNormalizeNaming.getBlobName(),
                        ICancellationTokenProvider.Extensions.fallbackToProvider(cancellationTokenProvider, cancellationToken)
                )
        );
    }

    @Override
    public Boolean exists(String name, CancellationToken cancellationToken) {
        BlobNormalizeNaming blobNormalizeNaming = blobNormalizeNamingService.normalizeNaming(configuration, containerName, name);

        return provider.exists(
                new BlobProviderExistsArgs(
                        blobNormalizeNaming.getContainerName(),
                        configuration,
                        blobNormalizeNaming.getBlobName(),
                        ICancellationTokenProvider.Extensions.fallbackToProvider(cancellationTokenProvider, cancellationToken)
                )
        );
    }

    @Override
    public InputStream get(String name, CancellationToken cancellationToken) {
        InputStream stream = getOrNull(name, cancellationToken);

        if (stream == null) {
            //TODO: Consider to throw some type of "not found" exception and handle on the HTTP status side
            throw new SystemException(
                    String.format("Could not found the requested BLOB '%s' in the container '%s'!", name, containerName));
        }

        return stream;
    }

    public InputStream getOrNull(String name, CancellationToken cancellationToken) {
        BlobNormalizeNaming blobNormalizeNaming =
                blobNormalizeNamingService.normalizeNaming(configuration, containerName, name);

        return provider.getOrNull(
                new BlobProviderGetArgs(
                        blobNormalizeNaming.getContainerName(),
                        configuration,
                        blobNormalizeNaming.getBlobName(),
                        ICancellationTokenProvider.Extensions.fallbackToProvider(cancellationTokenProvider, cancellationToken)
                )
        );
    }

    public String getContainerName() {
        return containerName;
    }

    public BlobContainerConfiguration getConfiguration() {
        return configuration;
    }

    public IBlobProvider getProvider() {
        return provider;
    }

    public ICancellationTokenProvider getCancellationTokenProvider() {
        return cancellationTokenProvider;
    }

    public IBlobNormalizeNamingService getBlobNormalizeNamingService() {
        return blobNormalizeNamingService;
    }

}
