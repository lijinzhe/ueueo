package com.ueueo;

import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-18 10:57
 */
public class DisposeAction implements IDisposable {

    private final Runnable action;

    public DisposeAction(@NonNull Runnable action) {
        Assert.notNull(action, "action must not null.");
        this.action = action;
    }

    @Override
    public void dispose() {
        action.run();
    }
}
