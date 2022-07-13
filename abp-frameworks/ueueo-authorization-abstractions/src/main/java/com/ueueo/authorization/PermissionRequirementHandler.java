package com.ueueo.authorization;

import com.ueueo.authorization.permissions.IPermissionChecker;
import com.ueueo.security.principal.ClaimsPrincipal;
import org.springframework.lang.Nullable;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;

import java.util.function.Supplier;

public class PermissionRequirementHandler implements AuthorizationManager<String> {

    private final IPermissionChecker permissionChecker;

    public PermissionRequirementHandler(IPermissionChecker permissionChecker) {
        this.permissionChecker = permissionChecker;
    }

    @Nullable
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, String requirement) {
        if (permissionChecker.isGranted((ClaimsPrincipal) authentication.get(), requirement)) {
            return new AuthorizationDecision(true);
        }
        return new AuthorizationDecision(false);
    }
}
