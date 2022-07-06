package com.ueueo.authorization.permissions;

import com.ueueo.multitenancy.ICurrentTenant;

import java.util.ArrayList;
import java.util.List;

public class PermissionValueProviderManager implements IPermissionValueProviderManager {

    private List<IPermissionValueProvider> providers;
    private IPermissionStore permissionStore;
    private ICurrentTenant currentTenant;

    public PermissionValueProviderManager(IPermissionStore permissionStore, ICurrentTenant currentTenant) {
        this.permissionStore = permissionStore;
        this.currentTenant = currentTenant;
        this.providers = new ArrayList<>();
        this.providers.add(new UserPermissionValueProvider(permissionStore));
        this.providers.add(new RolePermissionValueProvider(permissionStore));
        this.providers.add(new ClientPermissionValueProvider(permissionStore, currentTenant));
    }

    @Override
    public List<IPermissionValueProvider> getValueProviders() {
        return providers;
    }

    public IPermissionStore getPermissionStore() {
        return permissionStore;
    }

    public ICurrentTenant getCurrentTenant() {
        return currentTenant;
    }
}
