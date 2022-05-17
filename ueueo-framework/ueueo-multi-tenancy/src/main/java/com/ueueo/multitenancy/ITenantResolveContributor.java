package com.ueueo.multitenancy;

import com.ueueo.multitenancy.threading.MultiTenancyAsyncTaskExecutor;

import java.util.concurrent.Future;

/**
 * @author Lee
 * @date 2022-05-13 21:29
 */
public interface ITenantResolveContributor {
    String getName();

    void resolve(ITenantResolveContext context);

    default Future<?> resolveAsync(ITenantResolveContext context) {
        return MultiTenancyAsyncTaskExecutor.INSTANCE.submit(() -> resolve(context));
    }
}
