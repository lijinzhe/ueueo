package com.ueueo.blobstoring;

import com.ueueo.threading.CancellationToken;
import org.springframework.lang.NonNull;

public class BlobProviderGetArgs extends BlobProviderArgs
{
    public BlobProviderGetArgs(
        @NonNull String containerName,
        @NonNull BlobContainerConfiguration configuration,
        @NonNull String blobName,
        CancellationToken cancellationToken)
    {
        super(
                containerName,
                configuration,
                blobName,
                cancellationToken);
    }
}
