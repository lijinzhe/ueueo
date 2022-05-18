package com.ueueo.multitenancy;

import com.ueueo.BusinessException;
import com.ueueo.ID;
import org.slf4j.event.Level;

/**
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
    public TenantConfiguration get(boolean saveResolveResult) {
        TenantResolveResult resolveResult = tenantResolver.resolveTenantIdOrName();
        tenantResolveResultAccessor.setResult(resolveResult);
        TenantConfiguration tenant = null;
        if (resolveResult.getTenantIdOrName() != null) {
            tenant = findTenant(resolveResult.getTenantIdOrName());
            if (tenant == null) {
                throw new BusinessException("Volo.AbpIo.MultiTenancy:010001", "Tenant not found!", "There is no tenant with the tenant id or name: " + resolveResult.getTenantIdOrName(), null, Level.WARN);
            }
            if (tenant.getIsActive() == null || !tenant.getIsActive()) {
                throw new BusinessException("Volo.AbpIo.MultiTenancy:010002", "Tenant not active!", "The tenant is no active with the tenant id or name: " + resolveResult.getTenantIdOrName(), null, Level.WARN);
            }
        }
        return tenant;
    }

    protected TenantConfiguration findTenant(String tenantIdOrName) {
        try {
            ID tenantId = ID.valueOf(tenantIdOrName);
            return tenantStore.find(tenantId);
        } catch (NumberFormatException e) {
            return tenantStore.find(tenantIdOrName);
        }
    }

}
