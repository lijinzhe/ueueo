package com.ueueo.authorization.permissions;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 19:57
 */
public class PermissionStateContext {

    private PermissionDefinition permission;

    public PermissionDefinition getPermission() {
        return permission;
    }

    public void setPermission(PermissionDefinition permission) {
        this.permission = permission;
    }
}
