package com.ueueo.ui.navigation;

import com.ueueo.util.Check;
import com.ueueo.simplestatechecking.IHasSimpleStateCheckers;
import com.ueueo.simplestatechecking.ISimpleStateChecker;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lee
 * @date 2022-06-29 09:47
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ApplicationMenuItem implements IHasMenuItems, IHasSimpleStateCheckers<ApplicationMenuItem> {

    /**
     * Default <see cref="Order"/> value of a menu item.
     */
    public final int DefaultOrder = 1000;

    /**
     * Unique name of the menu in the application.
     */
    @NonNull
    @EqualsAndHashCode.Include
    public String name;
    /**
     * Display name of the menu item.
     */
    @NonNull
    public String displayName;

    /**
     * The Display order of the menu.
     * Default value: 1000.
     */
    public int order;

    /**
     * The URL to navigate when this menu item is selected.
     */
    @Nullable
    public String url;

    /**
     * Icon of the menu item if exists.
     */
    @Nullable
    public String icon;

    /**
     * Target of the menu item. Can be null, "_blank", "_self", "_parent", "_top" or a frame name for web applications.
     */
    @Nullable
    public String target;

    /**
     * Can be used to disable this menu item.
     */
    public boolean isDisabled;

    @NonNull
    public ApplicationMenuItemList items;

    @Nullable
    //    [Obsolete("Use RequirePermissions extension method.")]
    public String requiredPermissionName;

    public List<ISimpleStateChecker<ApplicationMenuItem>> stateCheckers;

    /**
     * Can be used to store a custom object related to this menu item. Optional.
     */
    public Object customData;

    /**
     * Can be used to render the element with a specific Id for DOM selections.
     */
    public String elementId;

    /**
     * Can be used to render the element with extra CSS classes.
     */
    public String cssClass;

    public ApplicationMenuItem(
            @NonNull String name,
            @NonNull String displayName,
            @Nullable String url,
            @Nullable String icon,
            Integer order,
            Object customData,
            @Nullable String target,
            String elementId,
            String cssClass,
            @Nullable String requiredPermissionName) {
        Check.notNullOrWhiteSpace(name, "Name must not null or white space.");
        Check.notNullOrWhiteSpace(displayName, "DisplayName must not null or white space.");

        this.name = name;
        this.displayName = displayName;
        this.url = url;
        this.icon = icon;
        this.order = order != null ? order : DefaultOrder;
        this.customData = customData;
        this.target = target;
        this.elementId = elementId != null ? elementId : getDefaultElementId();
        this.cssClass = cssClass;
        this.requiredPermissionName = requiredPermissionName;
        stateCheckers = new ArrayList<>();
        items = new ApplicationMenuItemList();
    }

    /**
     * Adds a <see cref="ApplicationMenuItem"/> to <see cref="Items"/>.
     *
     * @param menuItem ApplicationMenuItem to be added
     *
     * @return This <see cref="ApplicationMenuItem"/> object
     */
    public ApplicationMenuItem addItem(@NonNull ApplicationMenuItem menuItem) {
        items.add(menuItem);
        return this;
    }

    private String getDefaultElementId() {
        return "MenuItem_" + name;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        Assert.isTrue(StringUtils.isNotBlank(displayName), "DisplayName must not blank!");
        this.displayName = displayName;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * Returns true if this menu item has no child <see cref="Items"/>.
     */
    public boolean isLeaf() {
        return items == null || items.isEmpty();
    }

    @Nullable
    public String getUrl() {
        return url;
    }

    public void setUrl(@Nullable String url) {
        this.url = url;
    }

    @Nullable
    public String getIcon() {
        return icon;
    }

    public void setIcon(@Nullable String icon) {
        this.icon = icon;
    }

    @Nullable
    public String getTarget() {
        return target;
    }

    public void setTarget(@Nullable String target) {
        this.target = target;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }

    @Override
    @NonNull
    public ApplicationMenuItemList getItems() {
        return items;
    }

    @Nullable
    public String getRequiredPermissionName() {
        return requiredPermissionName;
    }

    public void setRequiredPermissionName(@Nullable String requiredPermissionName) {
        this.requiredPermissionName = requiredPermissionName;
    }

    @Override
    public List<ISimpleStateChecker<ApplicationMenuItem>> getStateCheckers() {
        return stateCheckers;
    }

    public Object getCustomData() {
        return customData;
    }

    public void setCustomData(Object customData) {
        this.customData = customData;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    @Override
    public String toString() {
        return "[ApplicationMenuItem] Name = " + name;
    }
}
