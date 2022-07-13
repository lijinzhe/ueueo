package com.ueueo.blobstoring;

import com.ueueo.threading.CancellationToken;
import org.springframework.lang.NonNull;

public class BlobProviderExistsArgs extends BlobProviderArgs {
    public BlobProviderExistsArgs(
            @NonNull String containerName,
            @NonNull BlobContainerConfiguration configuration,
            @NonNull String blobName,
            CancellationToken cancellationToken) {
        super(
                containerName,
                configuration,
                blobName,
                cancellationToken);
    }
}
