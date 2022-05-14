package com.ueueo.authorization.abstractions.permissions;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import com.ueueo.multitenancy.MultiTenancySides;

import java.util.*;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 15:03
 */
public class PermissionGroupDefinition {

    /** Unique name of the group. */
    private String name;
    private String displayName;
    private Map<String, Object> properties;

    /**
     * MultiTenancy side.
     * Default: {@link MultiTenancySides#Both}
     */
    private MultiTenancySides multiTenancySide;

    private List<PermissionDefinition> permissions;

    protected PermissionGroupDefinition(
            String name,
            String displayName,
            MultiTenancySides multiTenancySide) {
        this.name = name;
        this.displayName = displayName;
        this.multiTenancySide = multiTenancySide != null ? multiTenancySide : MultiTenancySides.Both;

        this.properties = new HashMap<>();
        this.permissions = new ArrayList<>();
    }

    public PermissionDefinition AddPermission(String name, String displayName, MultiTenancySides multiTenancySide, Boolean isEnabled) {
        PermissionDefinition permission = new PermissionDefinition(name, displayName, multiTenancySide, isEnabled);
        permissions.add(permission);
        return permission;
    }

    public List<PermissionDefinition> getPermissionsWithChildren() {
        List<PermissionDefinition> permissions = new ArrayList<>();

        for (PermissionDefinition permission : this.permissions) {
            addPermissionToListRecursively(permissions, permission);
        }

        return permissions;
    }

    private void addPermissionToListRecursively(List<PermissionDefinition> permissions, PermissionDefinition permission) {
        permissions.add(permission);

        for (PermissionDefinition child : permission.getChildren()) {
            addPermissionToListRecursively(permissions, child);
        }
    }

    @Nullable
    public PermissionDefinition GetPermissionOrNull(@NonNull String name) {
        Objects.requireNonNull(name);

        return getPermissionOrNullRecursively(permissions, name);
    }

    private PermissionDefinition getPermissionOrNullRecursively(List<PermissionDefinition> permissions, String name) {
        for (PermissionDefinition permission : permissions) {
            if (permission.getName().equals(name)) {
                return permission;
            }

            PermissionDefinition childPermission = getPermissionOrNullRecursively(permission.getChildren(), name);
            if (childPermission != null) {
                return childPermission;
            }
        }

        return null;
    }

    /**
     * Gets a key-value on the properties
     *
     * @param name Name of the property
     *
     * @return Returns the value in the properties dictionary by given name
     * Returns null if given name is not present in the properties dictionary.
     */
    public Object getProperty(String name) {
        return this.properties.get(name);
    }

    /**
     * Sets a key-value on the properties
     *
     * @param name  Name of the property
     * @param value Value of the property
     */
    public void setProperty(String name, Object value) {
        this.properties.put(name, value);
    }

    public String getName() {
        return name;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public MultiTenancySides getMultiTenancySide() {
        return multiTenancySide;
    }

    public void setMultiTenancySide(MultiTenancySides multiTenancySide) {
        this.multiTenancySide = multiTenancySide;
    }

    public List<PermissionDefinition> getPermissions() {
        return permissions;
    }
}
