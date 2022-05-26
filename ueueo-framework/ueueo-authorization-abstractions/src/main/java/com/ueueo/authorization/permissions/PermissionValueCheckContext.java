package com.ueueo.authorization.permissions;

import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 16:35
 */
public class PermissionValueCheckContext {
    @NonNull
    private PermissionDefinition permission;

    public PermissionValueCheckContext(@NonNull PermissionDefinition permission) {
        Objects.requireNonNull(permission);
        this.permission = permission;
    }

    public PermissionDefinition getPermission() {
        return permission;
    }
}
