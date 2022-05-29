package com.ueueo.content;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-29 11:41
 */
@Getter
public class RemoteStreamContent implements IRemoteStreamContent {

    private final InputStream stream;
    private final boolean disposeStream;
    private boolean disposed = false;
    private final String fileName;
    private String contentType = "application/octet-stream";
    private final Long contentLength;

    public RemoteStreamContent(InputStream stream, String fileName, String contentType, Long readOnlyLength, Boolean disposeStream) {
        this.stream = stream;
        this.disposeStream = disposeStream != null ? disposeStream : true;
        this.fileName = fileName;
        if (contentType != null) {
            this.contentType = contentType;
        }
        this.contentLength = readOnlyLength;
    }

    @Override
    public void dispose() {
        if (!disposed && disposeStream) {
            disposed = true;
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
