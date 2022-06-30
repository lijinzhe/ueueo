package com.ueueo.authorization.permissions;

import com.ueueo.exception.SystemException;
import org.springframework.beans.factory.BeanFactory;

import java.util.*;
import java.util.stream.Collectors;

public class PermissionDefinitionManager implements IPermissionDefinitionManager {
    protected Map<String, PermissionGroupDefinition> permissionGroupDefinitions;

    protected Map<String, PermissionDefinition> permissionDefinitions;

    protected AbpPermissionOptions options;

    private BeanFactory beanFactory;

    public PermissionDefinitionManager(
            AbpPermissionOptions options,
            BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        this.options = options;
        this.permissionDefinitions = new HashMap<>();
        this.permissionDefinitions.putAll(createPermissionDefinitions());
        this.permissionGroupDefinitions = new HashMap<>();
        this.permissionGroupDefinitions.putAll(createPermissionGroupDefinitions());
    }

    @Override
    public PermissionDefinition get(String name) {
        PermissionDefinition permission = getOrNull(name);

        if (permission == null) {
            throw new SystemException("Undefined permission: " + name);
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
            throw new SystemException("Duplicate permission name: " + permission.getName());
        }
        permissions.put(permission.getName(), permission);

        for (PermissionDefinition child : permission.getChildren()) {
            addPermissionToDictionaryRecursively(permissions, child);
        }
    }

    protected Map<String, PermissionGroupDefinition> createPermissionGroupDefinitions() {

        PermissionDefinitionContext context = new PermissionDefinitionContext();

        List<IPermissionDefinitionProvider> providers = options
                .getDefinitionProviders()
                .stream().map(beanFactory::getBean)
                .collect(Collectors.toList());

        for (IPermissionDefinitionProvider provider : providers) {
            provider.preDefine(context);
        }

        for (IPermissionDefinitionProvider provider : providers) {
            provider.define(context);
        }

        for (IPermissionDefinitionProvider provider : providers) {
            provider.postDefine(context);
        }

        return context.getGroups();

    }
}
