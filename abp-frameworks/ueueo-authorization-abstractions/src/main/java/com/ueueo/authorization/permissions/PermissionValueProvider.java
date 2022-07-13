package com.ueueo.authorization.permissions;

/**
 * @author Lee
 * @date 2021-08-26 19:58
 */
public abstract class PermissionValueProvider implements IPermissionValueProvider {

    protected IPermissionStore permissionStore;

    protected PermissionValueProvider(IPermissionStore permissionStore) {
        this.permissionStore = permissionStore;
    }

}
