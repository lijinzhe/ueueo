package com.ueueo.securitylog;

import com.ueueo.security.threading.SecurityAsyncTaskExecutor;

import java.util.concurrent.Future;

/**
 * @author Lee
 * @date 2022-05-17 10:44
 */
public interface ISecurityLogStore {
    void save(SecurityLogInfo securityLogInfo);

    default Future<?> saveAsync(SecurityLogInfo securityLogInfo) {
        return SecurityAsyncTaskExecutor.INSTANCE.submit(() -> save(securityLogInfo));
    }
}
