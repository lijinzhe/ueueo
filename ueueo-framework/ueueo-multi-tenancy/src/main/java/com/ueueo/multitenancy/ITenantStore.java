package com.ueueo.multitenancy;

import com.ueueo.multitenancy.threading.MultiTenancyAsyncTaskExecutor;

import java.util.concurrent.Future;

/**
 * @author Lee
 * @date 2022-05-13 22:17
 */
public interface ITenantStore {

    TenantConfiguration find(String name);

    TenantConfiguration find(Long id);

    default Future<TenantConfiguration> findAsync(String name) {
        return MultiTenancyAsyncTaskExecutor.INSTANCE.submit(() -> find(name));
    }

    default Future<TenantConfiguration> findAsync(Long id) {
        return MultiTenancyAsyncTaskExecutor.INSTANCE.submit(() -> find(id));
    }
}
