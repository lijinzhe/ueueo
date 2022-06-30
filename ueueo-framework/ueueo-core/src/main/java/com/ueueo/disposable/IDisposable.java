package com.ueueo.disposable;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author Lee
 * @date 2022-05-18 10:57
 */
public interface IDisposable extends Closeable {

    void dispose();

    @Override
    default void close() throws IOException {
        dispose();
    }
}
