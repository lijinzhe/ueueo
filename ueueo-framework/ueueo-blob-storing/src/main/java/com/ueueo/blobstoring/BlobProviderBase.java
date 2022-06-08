package com.ueueo.blobstoring;

import com.ueueo.threading.CancellationToken;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public abstract class BlobProviderBase implements IBlobProvider {

    protected InputStream tryCopyToMemoryStream(InputStream stream, CancellationToken cancellationToken) {
        if (stream == null) {
            return null;
        }

        try {
            return IOUtils.toBufferedInputStream(stream);
        } catch (IOException e) {
            return null;
        }
    }
}
