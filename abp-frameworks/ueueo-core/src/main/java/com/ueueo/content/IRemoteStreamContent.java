package com.ueueo.content;

import com.ueueo.disposable.IDisposable;

import java.io.InputStream;

/**
 * @author Lee
 * @date 2022-05-29 11:39
 */
public interface IRemoteStreamContent extends IDisposable {
    String getFileName();

    String getContentType();

    Long getContentLength();

    InputStream getStream();
}
