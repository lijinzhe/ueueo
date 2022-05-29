package com.ueueo;

/**
 * @author Lee
 * @date 2022-05-29 13:34
 */
public class NullDisposable implements IDisposable {

    public static final NullDisposable INSTANCE = new NullDisposable();

    private NullDisposable() {

    }

    @Override
    public void dispose() {

    }
}
