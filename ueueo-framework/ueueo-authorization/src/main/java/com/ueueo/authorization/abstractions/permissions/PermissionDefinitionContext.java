package com.ueueo.authorization.abstractions.permissions;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import com.ueueo.core.AbpException;
import com.ueueo.multitenancy.MultiTenancySides;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * TODO ABP代码
 *
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
        return null;
    }

    @Override
    public @NonNull PermissionGroupDefinition getGroupOrNull(String name) {
        return null;
    }

    @Override
    public @Nullable PermissionGroupDefinition addGroup(@NonNull String name, String displayName, MultiTenancySides multiTenancySide)  throws AbpException{
        Objects.requireNonNull(name);
        if (groups.containsKey(name)) {
            throw new AbpException("There is already an existing permission group with name: " + name);
        }
        PermissionGroupDefinition definition = new PermissionGroupDefinition(name, displayName, multiTenancySide);
        groups.put(name,definition);
        return definition;
    }

    @Override
    public void removeGroup(String name) {

    }

    @Override
    public @Nullable PermissionDefinition getPermissionOrNull(@NonNull String name) {
        return null;
    }

    public Map<String, PermissionGroupDefinition> getGroups() {
        return groups;
    }

}
