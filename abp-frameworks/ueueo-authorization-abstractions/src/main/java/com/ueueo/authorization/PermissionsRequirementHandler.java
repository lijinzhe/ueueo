package com.ueueo.authorization;

import com.ueueo.authorization.permissions.IPermissionChecker;
import com.ueueo.authorization.permissions.MultiplePermissionGrantResult;
import com.ueueo.authorization.permissions.PermissionGrantResult;
import com.ueueo.security.principal.ClaimsPrincipal;
import org.springframework.lang.Nullable;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;

import java.util.function.Supplier;

public class PermissionsRequirementHandler implements AuthorizationManager<PermissionsRequirement> {

    private final IPermissionChecker permissionChecker;

    public PermissionsRequirementHandler(IPermissionChecker permissionChecker) {
        this.permissionChecker = permissionChecker;
    }

    @Nullable
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, PermissionsRequirement requirement) {
        MultiplePermissionGrantResult multiplePermissionGrantResult = permissionChecker.isGranted((ClaimsPrincipal) authentication.get(), requirement.getPermissionNames());

        if (requirement.isRequiresAll() ?
                multiplePermissionGrantResult.allGranted() :
                multiplePermissionGrantResult.getResult().values().stream().anyMatch(PermissionGrantResult.Granted::equals)) {
            return new AuthorizationDecision(true);
        }
        return new AuthorizationDecision(false);
    }
}
