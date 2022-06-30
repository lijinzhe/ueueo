package com.ueueo.blobstoring;

import com.ueueo.threading.CancellationToken;
import org.springframework.lang.NonNull;

import java.io.InputStream;
import java.util.Objects;

public class BlobProviderSaveArgs extends BlobProviderArgs {
    @NonNull
    private InputStream blobStream;

    private boolean overrideExisting;

    public BlobProviderSaveArgs(
            @NonNull String containerName,
            @NonNull BlobContainerConfiguration configuration,
            @NonNull String blobName,
            @NonNull InputStream blobStream,
            boolean overrideExisting,
            CancellationToken cancellationToken) {
        super(
                containerName,
                configuration,
                blobName,
                cancellationToken);
        this.blobStream = Objects.requireNonNull(blobStream);
        this.overrideExisting = overrideExisting;
    }

    @NonNull
    public InputStream getBlobStream() {
        return blobStream;
    }

    public boolean isOverrideExisting() {
        return overrideExisting;
    }
}
