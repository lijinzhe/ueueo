package com.ueueo.authorization.abstractions.permissions;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 19:58
 */
public abstract class PermissionValueProvider implements IPermissionValueProvider {

    protected IPermissionStore permissionStore;

    protected PermissionValueProvider(IPermissionStore permissionStore) {
        this.permissionStore = permissionStore;
    }

    public IPermissionStore getPermissionStore() {
        return permissionStore;
    }

}
