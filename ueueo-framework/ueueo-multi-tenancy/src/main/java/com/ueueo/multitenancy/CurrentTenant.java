package com.ueueo.multitenancy;

import java.util.Optional;

/**
 *
 * @author Lee
 * @date 2022-05-13 20:53
 */
public class CurrentTenant implements ICurrentTenant {

    private final ICurrentTenantAccessor currentTenantAccessor;

    public CurrentTenant(ICurrentTenantAccessor currentTenantAccessor) {
        this.currentTenantAccessor = currentTenantAccessor;
    }

    @Override
    public Boolean getIsAvailable() {
        return false;
    }

    @Override
    public Integer getId() {
        return Optional.ofNullable(currentTenantAccessor.getCurrent())
                .map(BasicTenantInfo::getTenantId)
                .orElse(null);
    }

    @Override
    public String getName() {
        return Optional.ofNullable(currentTenantAccessor.getCurrent())
                .map(BasicTenantInfo::getName)
                .orElse(null);
    }

    @Override
    public void change(Integer tenantId, String name) {
        currentTenantAccessor.setCurrent(new BasicTenantInfo(tenantId, name));
    }
}
