package com.ueueo.blobstoring;

import com.ueueo.threading.CancellationToken;
import org.springframework.lang.NonNull;

import java.util.Objects;

public abstract class BlobProviderArgs {
    @NonNull
    private String containerName;

    @NonNull
    private BlobContainerConfiguration configuration;

    @NonNull
    private String blobName;

    public CancellationToken cancellationToken;

    protected BlobProviderArgs(
            @NonNull String containerName,
            @NonNull BlobContainerConfiguration configuration,
            @NonNull String blobName,
            CancellationToken cancellationToken) {
        this.containerName = Objects.requireNonNull(containerName);
        this.configuration = Objects.requireNonNull(configuration);
        this.blobName = Objects.requireNonNull(blobName);
        this.cancellationToken = cancellationToken;
    }

    @NonNull
    public String getContainerName() {
        return containerName;
    }

    @NonNull
    public BlobContainerConfiguration getConfiguration() {
        return configuration;
    }

    @NonNull
    public String getBlobName() {
        return blobName;
    }

    public CancellationToken getCancellationToken() {
        return cancellationToken;
    }
}
