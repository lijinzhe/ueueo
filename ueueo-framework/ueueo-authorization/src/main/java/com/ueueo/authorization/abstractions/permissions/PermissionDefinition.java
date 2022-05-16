package com.ueueo.authorization.abstractions.permissions;

import com.ueueo.simplestatechecking.IHasSimpleStateCheckers;
import com.ueueo.simplestatechecking.ISimpleStateChecker;
import com.ueueo.multitenancy.MultiTenancySides;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 15:12
 */
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

    private String displayName;

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

    protected PermissionDefinition(@NonNull String name, String displayName, MultiTenancySides multiTenancySide, Boolean isEnabled) {
        this.name = name;
        this.displayName = displayName;
        this.multiTenancySide = multiTenancySide != null ? multiTenancySide : MultiTenancySides.Both;
        this.isEnabled = isEnabled != null ? isEnabled : true;
        this.properties = new HashMap<>();
        this.allowedProviders = new ArrayList<>();
        this.stateCheckers = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public PermissionDefinition addChild(@NonNull String name, String displayName, MultiTenancySides multiTenancySide, Boolean isEnabled) {
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
    public PermissionDefinition WithProperty(String key, Object value) {
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
    public PermissionDefinition WithProviders(List<String> providers) {
        if (providers != null && !providers.isEmpty()) {
            this.allowedProviders.addAll(providers);
        }
        return this;
    }

    public String getName() {
        return name;
    }

    public PermissionDefinition getParent() {
        return parent;
    }

    public void setParent(PermissionDefinition parent) {
        this.parent = parent;
    }

    public List<PermissionDefinition> getChildren() {
        return children;
    }

    public MultiTenancySides getMultiTenancySide() {
        return multiTenancySide;
    }

    public void setMultiTenancySide(MultiTenancySides multiTenancySide) {
        this.multiTenancySide = multiTenancySide;
    }

    public List<String> getAllowedProviders() {
        return allowedProviders;
    }

    @Override
    public List<ISimpleStateChecker<PermissionDefinition>> stateCheckers() {
        return stateCheckers;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }
}
