package com.ueueo.authorization;

import com.ueueo.authorization.microsoft.AuthorizationHandler;
import com.ueueo.authorization.microsoft.AuthorizationHandlerContext;
import com.ueueo.authorization.permissions.IPermissionChecker;
import com.ueueo.authorization.permissions.MultiplePermissionGrantResult;
import com.ueueo.authorization.permissions.PermissionGrantResult;

public class PermissionsRequirementHandler extends AuthorizationHandler<PermissionsRequirement> {

    private final IPermissionChecker permissionChecker;

    public PermissionsRequirementHandler(IPermissionChecker permissionChecker) {
        this.permissionChecker = permissionChecker;
    }

    @Override
    protected void handleRequirement(AuthorizationHandlerContext context, PermissionsRequirement requirement) {
        MultiplePermissionGrantResult multiplePermissionGrantResult = permissionChecker.isGranted(context.getUser(), requirement.getPermissionNames());

        if (requirement.isRequiresAll() ?
                multiplePermissionGrantResult.allGranted() :
                multiplePermissionGrantResult.getResult().values().stream().anyMatch(PermissionGrantResult.Granted::equals)) {
            context.succeed(requirement);
        }
    }
}
