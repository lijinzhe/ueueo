package com.ueueo.data;

import com.ueueo.data.threading.DataAsyncTaskExecutor;
import org.springframework.lang.NonNull;

import java.util.concurrent.Future;

/**
 * @author Lee
 * @date 2022-05-16 11:33
 */
public interface IConnectionStringResolver {

    @NonNull
    String resolve(String connectionStringName);

    default Future<String> resolveAsync(String connectionStringName) {
        return DataAsyncTaskExecutor.INSTANCE.submit(() -> resolve(connectionStringName));
    }

}
