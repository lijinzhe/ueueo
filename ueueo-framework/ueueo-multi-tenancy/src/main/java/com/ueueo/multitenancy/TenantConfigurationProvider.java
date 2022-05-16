package com.ueueo.multitenancy;

import com.ueueo.core.BusinessException;
import com.ueueo.core.logging.LogLevel;
import com.ueueo.multitenancy.threading.MultiTenancyAsyncTaskExecutor;

import java.util.concurrent.Future;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-13 22:25
 */
public class TenantConfigurationProvider implements ITenantConfigurationProvider {

    protected final ITenantResolver tenantResolver;
    protected final ITenantStore tenantStore;
    protected final ITenantResolveResultAccessor tenantResolveResultAccessor;

    public TenantConfigurationProvider(ITenantResolver tenantResolver, ITenantStore tenantStore, ITenantResolveResultAccessor tenantResolveResultAccessor) {
        this.tenantResolver = tenantResolver;
        this.tenantStore = tenantStore;
        this.tenantResolveResultAccessor = tenantResolveResultAccessor;
    }

    @Override
    public Future<TenantConfiguration> getAsync(boolean saveResolveResult) {
        return MultiTenancyAsyncTaskExecutor.INSTANCE.submit(() -> {
            TenantResolveResult resolveResult = tenantResolver.resolveTenantIdOrNameAsync().get();
            if (resolveResult != null) {
                tenantResolveResultAccessor.setResult(resolveResult);
            }
            TenantConfiguration tenant = null;
            if (resolveResult.getTenantIdOrName() != null) {
                tenant = findTenantAsync(resolveResult.getTenantIdOrName()).get();
                if (tenant == null) {
                    throw new BusinessException("Volo.AbpIo.MultiTenancy:010001", "Tenant not found!", "There is no tenant with the tenant id or name: " + resolveResult.getTenantIdOrName(), null, LogLevel.WARN);
                }
                if (tenant.getIsActive() == null || !tenant.getIsActive()) {
                    throw new BusinessException("Volo.AbpIo.MultiTenancy:010002", "Tenant not active!", "The tenant is no active with the tenant id or name: " + resolveResult.getTenantIdOrName(), null, LogLevel.WARN);
                }
            }
            return tenant;
        });
    }

    protected Future<TenantConfiguration> findTenantAsync(String tenantIdOrName) {
        try {
            Long tenantId = Long.valueOf(tenantIdOrName);
            return tenantStore.findAsync(tenantId);
        } catch (NumberFormatException e) {
            return tenantStore.findAsync(tenantIdOrName);
        }
    }
}
