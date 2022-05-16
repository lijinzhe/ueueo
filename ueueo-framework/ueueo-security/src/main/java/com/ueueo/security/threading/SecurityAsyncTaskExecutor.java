package com.ueueo.security.threading;

import com.ueueo.threading.AsyncTaskExecutor;

import java.util.concurrent.TimeUnit;

/**
 * @author Lee
 * @date 2022-05-16 17:43
 */
public class SecurityAsyncTaskExecutor {
    public static final AsyncTaskExecutor INSTANCE = new AsyncTaskExecutor(4, 8, 0L,
            TimeUnit.MILLISECONDS, 1000, "ueueo-security-pool-%s");
}
