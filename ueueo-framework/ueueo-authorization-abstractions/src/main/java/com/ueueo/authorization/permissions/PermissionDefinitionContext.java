package com.ueueo.authorization.permissions;

import com.ueueo.AbpException;
import com.ueueo.localization.ILocalizableString;
import com.ueueo.multitenancy.MultiTenancySides;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Lee
 * @date 2021-08-26 19:37
 */
public class PermissionDefinitionContext implements IPermissionDefinitionContext {

    private Map<String, PermissionGroupDefinition> groups;

    public PermissionDefinitionContext() {
        this.groups = new HashMap<>();
    }

    @Override
    public PermissionGroupDefinition getGroup(@NonNull String name) {
        PermissionGroupDefinition group = getGroupOrNull(name);
        if (group == null) {
            throw new AbpException(String.format("Could not find a permission definition group with the given name: %s", name));
        }

        return group;
    }

    @Override
    public PermissionGroupDefinition getGroupOrNull(@NonNull String name) {
        Objects.requireNonNull(name);
        return groups.get(name);
    }

    @Override
    @NonNull
    public PermissionGroupDefinition addGroup(@NonNull String name, ILocalizableString displayName, MultiTenancySides multiTenancySide) {
        Objects.requireNonNull(name);
        if (groups.containsKey(name)) {
            throw new AbpException("There is already an existing permission group with name: " + name);
        }
        PermissionGroupDefinition definition = new PermissionGroupDefinition(name, displayName, multiTenancySide);
        groups.put(name, definition);
        return definition;
    }

    @Override
    public void removeGroup(@NonNull String name) {
        Objects.requireNonNull(name);
        PermissionGroupDefinition removed = groups.remove(name);
        if (removed == null) {
            throw new AbpException(String.format("Not found permission group with name: %s", name));
        }
    }

    @Override
    public PermissionDefinition getPermissionOrNull(@NonNull String name) {
        Objects.requireNonNull(name);
        for (PermissionGroupDefinition groupDefinition : groups.values()) {
            PermissionDefinition permissionDefinition = groupDefinition.getPermissionOrNull(name);
            if (permissionDefinition != null) {
                return permissionDefinition;
            }
        }
        return null;
    }

    public Map<String, PermissionGroupDefinition> getGroups() {
        return groups;
    }

}
