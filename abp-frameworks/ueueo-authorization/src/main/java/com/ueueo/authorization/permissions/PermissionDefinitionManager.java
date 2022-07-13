package com.ueueo.authorization.permissions;

import com.ueueo.exception.BaseException;

import java.util.*;

public class PermissionDefinitionManager implements IPermissionDefinitionManager {

    protected Map<String, PermissionGroupDefinition> permissionGroupDefinitions;

    protected Map<String, PermissionDefinition> permissionDefinitions;

    protected List<IPermissionDefinitionProvider> providers;

    public PermissionDefinitionManager() {
        this.providers = new ArrayList<>();
        this.permissionDefinitions = new HashMap<>();
        this.permissionDefinitions.putAll(createPermissionDefinitions());
        this.permissionGroupDefinitions = new HashMap<>();
        this.permissionGroupDefinitions.putAll(createPermissionGroupDefinitions());
    }

    @Override
    public PermissionDefinition get(String name) {
        PermissionDefinition permission = getOrNull(name);

        if (permission == null) {
            throw new BaseException("Undefined permission: " + name);
        }

        return permission;
    }

    @Override
    public PermissionDefinition getOrNull(String name) {
        Objects.requireNonNull(name);

        return permissionDefinitions.get(name);
    }

    @Override
    public List<PermissionDefinition> getPermissions() {
        return new ArrayList<>(permissionDefinitions.values());
    }

    @Override
    public List<PermissionGroupDefinition> getGroups() {
        return new ArrayList<>(permissionGroupDefinitions.values());
    }

    protected Map<String, PermissionDefinition> createPermissionDefinitions() {
        Map<String, PermissionDefinition> permissions = new HashMap<>();

        for (PermissionGroupDefinition groupDefinition : permissionGroupDefinitions.values()) {
            for (PermissionDefinition permission : groupDefinition.getPermissions()) {
                addPermissionToDictionaryRecursively(permissions, permission);
            }
        }

        return permissions;
    }

    protected void addPermissionToDictionaryRecursively(
            Map<String, PermissionDefinition> permissions,
            PermissionDefinition permission) {
        if (permissions.containsKey(permission.getName())) {
            throw new BaseException("Duplicate permission name: " + permission.getName());
        }
        permissions.put(permission.getName(), permission);

        for (PermissionDefinition child : permission.getChildren()) {
            addPermissionToDictionaryRecursively(permissions, child);
        }
    }

    protected Map<String, PermissionGroupDefinition> createPermissionGroupDefinitions() {

        PermissionDefinitionContext context = new PermissionDefinitionContext();

        for (IPermissionDefinitionProvider provider : getDefinitionProvider()) {
            provider.preDefine(context);
        }

        for (IPermissionDefinitionProvider provider : getDefinitionProvider()) {
            provider.define(context);
        }

        for (IPermissionDefinitionProvider provider : getDefinitionProvider()) {
            provider.postDefine(context);
        }

        return context.getGroups();

    }

    @Override
    public List<IPermissionDefinitionProvider> getDefinitionProvider() {
        return providers;
    }
}
