package com.ueueo.authorization.permissions;

import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 19:52
 */
public class PermissionDefinitionContextExtensions {

    /**
     * Finds and disables a permission with the given name/>.
     * Returns false if given permission was not found.
     *
     * @param context Permission definition context
     * @param name    Name of the permission
     *
     * @return Returns true if given permission was found.
     * Returns false if given permission was not found.
     */
    public static Boolean tryDisablePermission(@NonNull IPermissionDefinitionContext context, @NonNull String name) {
        Objects.requireNonNull(context);
        Objects.requireNonNull(name);

        PermissionDefinition permission = context.getPermissionOrNull(name);
        if (permission == null) {
            return false;
        }

        permission.setIsEnabled(false);
        return true;
    }
}
