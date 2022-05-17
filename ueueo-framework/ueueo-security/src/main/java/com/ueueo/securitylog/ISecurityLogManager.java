package com.ueueo.securitylog;

import com.ueueo.security.threading.SecurityAsyncTaskExecutor;

import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * @author Lee
 * @date 2022-05-17 11:28
 */
public interface ISecurityLogManager {
    void save(Consumer<SecurityLogInfo> saveAction);

    default Future<?> saveAsync(Consumer<SecurityLogInfo> saveAction) {
        return SecurityAsyncTaskExecutor.INSTANCE.submit(() -> save(saveAction));
    }
}
