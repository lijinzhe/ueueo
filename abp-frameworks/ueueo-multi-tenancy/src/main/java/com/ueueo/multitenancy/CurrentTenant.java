package com.ueueo.multitenancy;

import com.ueueo.ID;
import com.ueueo.disposable.IDisposable;

import java.util.Optional;

/**
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
    public ID getId() {
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
    public IDisposable change(ID tenantId, String name) {
        BasicTenantInfo preCurrent = currentTenantAccessor.getCurrent();
        currentTenantAccessor.setCurrent(new BasicTenantInfo(tenantId, name));
        return () -> currentTenantAccessor.setCurrent(preCurrent);
    }
}
