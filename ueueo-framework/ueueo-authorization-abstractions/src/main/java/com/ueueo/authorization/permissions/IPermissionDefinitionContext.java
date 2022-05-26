package com.ueueo.authorization.permissions;

import com.ueueo.localization.ILocalizableString;
import com.ueueo.multitenancy.MultiTenancySides;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-24 20:52
 */
public interface IPermissionDefinitionContext {

    /**
     * Gets a pre-defined permission group.
     * Throws <see cref="AbpException"/> if can not find the given group.
     *
     * @param name Name of the group
     *
     * @return
     */
    PermissionGroupDefinition getGroup(@NonNull String name);

    /**
     * Tries to get a pre-defined permission group.
     * Returns null if can not find the given group.
     *
     * @param name Name of the group
     *
     * @return
     */
    @NonNull
    PermissionGroupDefinition getGroupOrNull(String name);

    @NonNull
    default PermissionGroupDefinition addGroup(@NonNull String name, ILocalizableString displayName) {
        return this.addGroup(name, displayName, MultiTenancySides.Both);
    }

    @NonNull
    PermissionGroupDefinition addGroup(@NonNull String name, ILocalizableString displayName, MultiTenancySides multiTenancySide);

    void removeGroup(String name);

    @Nullable
    PermissionDefinition getPermissionOrNull(@NonNull String name);
}
