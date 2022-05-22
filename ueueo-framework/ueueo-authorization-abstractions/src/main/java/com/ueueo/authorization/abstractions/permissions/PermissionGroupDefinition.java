package com.ueueo.authorization.abstractions.permissions;

import com.ueueo.localization.FixedLocalizableString;
import com.ueueo.localization.ILocalizableString;
import com.ueueo.multitenancy.MultiTenancySides;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.*;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 15:03
 */
@Data
public class PermissionGroupDefinition {

    /** Unique name of the group. */
    private String name;
    private ILocalizableString displayName;
    @Setter(AccessLevel.PRIVATE)
    private Map<String, Object> properties;

    /**
     * MultiTenancy side.
     * Default: {@link MultiTenancySides#Both}
     */
    private MultiTenancySides multiTenancySide;

    private List<PermissionDefinition> permissions;

    protected PermissionGroupDefinition(@NonNull String name, ILocalizableString displayName, MultiTenancySides multiTenancySide) {
        Assert.notNull(name, "name must not null!");
        this.name = name;
        this.displayName = displayName != null ? displayName : new FixedLocalizableString(name);
        this.multiTenancySide = multiTenancySide != null ? multiTenancySide : MultiTenancySides.Both;

        this.properties = new HashMap<>();
        this.permissions = new ArrayList<>();
    }

    public PermissionDefinition addPermission(@NonNull String name, ILocalizableString displayName, MultiTenancySides multiTenancySide, Boolean isEnabled) {
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
    public PermissionDefinition getPermissionOrNull(@NonNull String name) {
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

}
