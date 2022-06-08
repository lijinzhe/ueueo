package com.ueueo.blobstoring;

import org.springframework.lang.NonNull;

public interface IBlobProviderSelector {
    @NonNull
    IBlobProvider get(@NonNull String containerName);
}
