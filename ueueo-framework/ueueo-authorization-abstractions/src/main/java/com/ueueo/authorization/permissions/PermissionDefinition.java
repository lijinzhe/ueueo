package com.ueueo.authorization.permissions;

import com.ueueo.localization.FixedLocalizableString;
import com.ueueo.localization.ILocalizableString;
import com.ueueo.multitenancy.MultiTenancySides;
import com.ueueo.simplestatechecking.IHasSimpleStateCheckers;
import com.ueueo.simplestatechecking.ISimpleStateChecker;
import lombok.Data;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lee
 * @date 2021-08-26 15:12
 */
@Data
public class PermissionDefinition implements IHasSimpleStateCheckers<PermissionDefinition> {

    /** Unique name of the permission. */
    private String name;

    /**
     * Parent of this permission if one exists.
     * If set, this permission can be granted only if parent is granted.
     */
    private PermissionDefinition parent;

    /**
     * MultiTenancy side.
     * Default: <see cref="MultiTenancySides.Both"/>
     */
    private MultiTenancySides multiTenancySide;

    /**
     * A list of allowed providers to get/set value of this permission.
     * An empty list indicates that all providers are allowed.
     */
    private List<String> allowedProviders;

    private List<ISimpleStateChecker<PermissionDefinition>> stateCheckers;

    private ILocalizableString displayName;

    private List<PermissionDefinition> children;

    /** Can be used to get/set custom properties for this permission definition. */
    private Map<String, Object> properties;

    /**
     * Indicates whether this permission is enabled or disabled.
     * A permission is normally enabled.
     * A disabled permission can not be granted to anyone, but it is still
     * will be available to check its value (while it will always be false).
     *
     * Disabling a permission would be helpful to hide a related application
     * functionality from users/clients.
     *
     * Default: true.
     */
    private Boolean isEnabled;

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

    protected PermissionDefinition(@NonNull String name, ILocalizableString displayName, MultiTenancySides multiTenancySide, Boolean isEnabled) {
        Assert.notNull(name, "name must not null!");
        this.name = name;
        this.displayName = displayName != null ? displayName : new FixedLocalizableString(name);
        this.multiTenancySide = multiTenancySide != null ? multiTenancySide : MultiTenancySides.Both;
        this.isEnabled = isEnabled != null ? isEnabled : true;
        this.properties = new HashMap<>();
        this.allowedProviders = new ArrayList<>();
        this.stateCheckers = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public PermissionDefinition addChild(@NonNull String name, ILocalizableString displayName, MultiTenancySides multiTenancySide, Boolean isEnabled) {
        PermissionDefinition child = new PermissionDefinition(name, displayName, multiTenancySide, isEnabled);
        child.setParent(this);
        this.children.add(child);
        return child;
    }

    /**
     * Sets a property in the properties dictionary.
     * This is a shortcut for nested calls on this object.
     *
     * @param key
     * @param value
     *
     * @return
     */
    public PermissionDefinition withProperty(String key, Object value) {
        this.properties.put(key, value);
        return this;
    }

    /**
     * Set the <see cref="StateProviders"/> property.
     * This is a shortcut for nested calls on this object.
     *
     * @param providers
     *
     * @return
     */
    public PermissionDefinition withProviders(List<String> providers) {
        if (providers != null && !providers.isEmpty()) {
            this.allowedProviders.addAll(providers);
        }
        return this;
    }
}
