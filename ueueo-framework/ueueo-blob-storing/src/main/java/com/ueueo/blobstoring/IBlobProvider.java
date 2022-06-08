package com.ueueo.blobstoring;

import java.io.InputStream;

public interface IBlobProvider {

    void save(BlobProviderSaveArgs args);

    Boolean delete(BlobProviderDeleteArgs args);

    Boolean exists(BlobProviderExistsArgs args);

    InputStream getOrNull(BlobProviderGetArgs args);
}
