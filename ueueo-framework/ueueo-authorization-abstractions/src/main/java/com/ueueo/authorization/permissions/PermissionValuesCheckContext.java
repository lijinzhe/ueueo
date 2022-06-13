package com.ueueo.authorization.permissions;

import com.ueueo.principal.ClaimsPrincipal;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Objects;

/**
 * @author Lee
 * @date 2021-08-26 16:36
 */
public class PermissionValuesCheckContext {
    @NonNull
    private List<PermissionDefinition> permissions;
    private ClaimsPrincipal principal;

    public PermissionValuesCheckContext(@NonNull List<PermissionDefinition> permissions, ClaimsPrincipal principal) {
        Objects.requireNonNull(permissions);

        this.permissions = permissions;
        this.principal = principal;
    }

    public List<PermissionDefinition> getPermissions() {
        return permissions;
    }

    public ClaimsPrincipal getPrincipal() {
        return principal;
    }
}
