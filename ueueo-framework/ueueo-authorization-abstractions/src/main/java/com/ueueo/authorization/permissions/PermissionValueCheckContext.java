package com.ueueo.authorization.permissions;

import com.ueueo.principal.ClaimsPrincipal;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 *
 * @author Lee
 * @date 2021-08-26 16:35
 */
@Getter
public class PermissionValueCheckContext {
    @NonNull
    private PermissionDefinition permission;
    private ClaimsPrincipal principal;

    public PermissionValueCheckContext(@NonNull PermissionDefinition permission, ClaimsPrincipal principal) {
        Objects.requireNonNull(permission);
        this.permission = permission;
        this.principal = principal;
    }
}
