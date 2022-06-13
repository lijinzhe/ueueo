package com.ueueo.threading;

/**
 * This class is used to ensure running of a code block only once.
 * It can be instantiated as a static object to ensure that the code block runs only once in the application lifetime.
 */
public class OneTimeRunner {
    private volatile boolean runBefore = false;

    public void run(Runnable action) {
        if (runBefore) {
            return;
        }

        synchronized (this) {
            if (runBefore) {
                return;
            }

            action.run();

            runBefore = true;
        }
    }
}
