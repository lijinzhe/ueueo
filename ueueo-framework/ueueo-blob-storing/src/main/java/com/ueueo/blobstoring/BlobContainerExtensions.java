package com.ueueo.blobstoring;

import com.ueueo.threading.CancellationToken;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BlobContainerExtensions {

    public static void save(
            IBlobContainer container,
            String name,
            byte[] bytes,
            boolean overrideExisting,
            CancellationToken cancellationToken
    ) {
        InputStream memoryStream = new ByteArrayInputStream(bytes);
        container.save(name,
                memoryStream,
                overrideExisting,
                cancellationToken);
    }

    public static byte[] getAllBytes(
            IBlobContainer container,
            String name,
            CancellationToken cancellationToken) {
        InputStream stream = container.get(name, cancellationToken);
        try {
            return IOUtils.toByteArray(stream);
        } catch (IOException e) {
            return null;
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static byte[] getAllBytesOrNull(
            IBlobContainer container,
            String name,
            CancellationToken cancellationToken) {
        InputStream stream = container.get(name, cancellationToken);
        if (stream == null) {
            return null;
        }

        try {
            return IOUtils.toByteArray(stream);
        } catch (IOException e) {
            return null;
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
