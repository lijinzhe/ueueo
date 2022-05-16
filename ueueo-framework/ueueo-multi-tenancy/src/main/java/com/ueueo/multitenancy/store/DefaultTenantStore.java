package com.ueueo.multitenancy.store;

import com.ueueo.multitenancy.ITenantStore;
import com.ueueo.multitenancy.TenantConfiguration;
import com.ueueo.multitenancy.threading.MultiTenancyAsyncTaskExecutor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.concurrent.Future;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-16 20:20
 */
public class DefaultTenantStore implements ITenantStore {

    private final AbpDefaultTenantStoreOptions options;

    public DefaultTenantStore(AbpDefaultTenantStoreOptions options) {
        this.options = options;
    }

    @Override
    public Future<TenantConfiguration> findAsync(String name) {
        return MultiTenancyAsyncTaskExecutor.INSTANCE.submit(() -> find(name));
    }

    @Override
    public Future<TenantConfiguration> findAsync(Long id) {
        return MultiTenancyAsyncTaskExecutor.INSTANCE.submit(() -> find(id));
    }

    @Override
    public TenantConfiguration find(String name) {
        if (CollectionUtils.isNotEmpty(options.getTenants())) {
            return options.getTenants().stream()
                    .filter(tenant -> StringUtils.equals(tenant.getName(), name))
                    .findFirst().orElse(null);
        }
        return null;
    }

    @Override
    public TenantConfiguration find(Long id) {
        if (CollectionUtils.isNotEmpty(options.getTenants())) {
            return options.getTenants().stream()
                    .filter(tenant -> Objects.equals(tenant.getId(), id))
                    .findFirst().orElse(null);
        }
        return null;
    }
}
