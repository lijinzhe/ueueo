package com.ueueo.data.threading;

import com.ueueo.threading.AsyncTaskExecutor;

import java.util.concurrent.TimeUnit;

/**
 * @author Lee
 * @date 2022-05-16 17:43
 */
public class DataAsyncTaskExecutor {
    public static final AsyncTaskExecutor INSTANCE = new AsyncTaskExecutor(4, 8, 0L,
            TimeUnit.MILLISECONDS, 1000, "ueueo-data-pool-%s");
}
