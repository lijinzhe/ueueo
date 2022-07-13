package com.ueueo.multitenancy.store;

import com.ueueo.ID;
import com.ueueo.multitenancy.ITenantStore;
import com.ueueo.multitenancy.TenantConfiguration;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Lee
 * @date 2022-05-16 20:20
 */
public class InMemoryTenantStore implements ITenantStore {

    private final List<TenantConfiguration> tenants;

    public InMemoryTenantStore() {
        this.tenants = new ArrayList<>();
    }

    public InMemoryTenantStore(List<TenantConfiguration> tenants) {
        this.tenants = new ArrayList<>();
        this.tenants.addAll(tenants);
    }

    @Override
    public TenantConfiguration find(String name) {
        if (CollectionUtils.isNotEmpty(tenants)) {
            return tenants.stream()
                    .filter(tenant -> StringUtils.equals(tenant.getName(), name))
                    .findFirst().orElse(null);
        }
        return null;
    }

    @Override
    public TenantConfiguration find(ID id) {
        if (CollectionUtils.isNotEmpty(tenants)) {
            return tenants.stream()
                    .filter(tenant -> Objects.equals(tenant.getId(), id))
                    .findFirst().orElse(null);
        }
        return null;
    }

    public void add(TenantConfiguration tenant) {
        this.tenants.add(tenant);
    }
}
