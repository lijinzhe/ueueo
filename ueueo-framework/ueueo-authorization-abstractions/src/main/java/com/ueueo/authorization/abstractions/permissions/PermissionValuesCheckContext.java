package com.ueueo.authorization.abstractions.permissions;

import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Objects;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 16:36
 */
public class PermissionValuesCheckContext {
    @NonNull
    private List<PermissionDefinition> permissions;

    public PermissionValuesCheckContext(@NonNull List<PermissionDefinition> permissions) {
        Objects.requireNonNull(permissions);

        this.permissions = permissions;
    }

    public List<PermissionDefinition> getPermissions() {
        return permissions;
    }
}
