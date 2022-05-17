package com.ueueo.multitenancy;

import com.ueueo.multitenancy.threading.MultiTenancyAsyncTaskExecutor;

import java.util.concurrent.Future;

/**
 * @author Lee
 * @date 2022-05-13 21:11
 */
public interface ITenantConfigurationProvider {
    /**
     * @param saveResolveResult 默认false
     *
     * @return
     */
    TenantConfiguration get(boolean saveResolveResult);

    default Future<TenantConfiguration> getAsync(boolean saveResolveResult) {
        return MultiTenancyAsyncTaskExecutor.INSTANCE.submit(() -> get(saveResolveResult));
    }
}
