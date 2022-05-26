package com.ueueo;

import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

/**
 * This class can be used to provide an action when
 * Dipose method is called.
 *
 * @author Lee
 * @date 2022-05-18 10:57
 */
public class DisposeAction implements IDisposable {

    private final Runnable action;

    /**
     * Creates a new <see cref="DisposeAction"/> object.
     *
     * @param action Action to be executed when this object is disposed.
     */
    public DisposeAction(@NonNull Runnable action) {
        Assert.notNull(action, "action must not null.");
        this.action = action;
    }

    @Override
    public void dispose() {
        action.run();
    }
}
