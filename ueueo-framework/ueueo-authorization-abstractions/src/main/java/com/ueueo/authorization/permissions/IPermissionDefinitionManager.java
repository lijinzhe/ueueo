package com.ueueo.authorization.permissions;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @author Lee
 * @date 2021-08-26 16:24
 */
public interface IPermissionDefinitionManager {
    @NonNull
    PermissionDefinition get(@NonNull String name);

    @Nullable
    PermissionDefinition getOrNull(@NonNull String name);

    List<PermissionDefinition> getPermissions();

    List<PermissionGroupDefinition> getGroups();
}
