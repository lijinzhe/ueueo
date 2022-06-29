package com.ueueo.ui.navigation;

import com.ueueo.AbpException;
import com.ueueo.Check;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * @author Lee
 * @date 2022-06-29 09:59
 */
public class ApplicationMenu implements IHasMenuItems {

    /** Unique name of the menu in the application. */
    @NonNull
    public String name;

    /**
     * Display name of the menu.
     * Default value is the <see cref="Name"
     */
    @NonNull
    private String displayName;

    @NonNull
    public ApplicationMenuItemList items;

    /**
     * Can be used to store a custom object related to this menu.
     *  TODO: Convert to dictionary!
     */
    @Nullable
    public Object customData;

    public ApplicationMenu(@NonNull String name, String displayName) {
        Check.notNullOrWhiteSpace(name, "Name must not null or white space!");

        this.name = name;
        this.displayName = displayName != null ? displayName : this.name;

        this.items = new ApplicationMenuItemList();
    }

    /**
     * Adds a <see cref="ApplicationMenuItem"/> to <see cref="Items"/>.
     *
     * @param menuItem ApplicationMenuItem to be added
     *
     * @return ApplicationMenu object
     */
    public ApplicationMenu addItem(@NonNull ApplicationMenuItem menuItem) {
        items.add(menuItem);
        return this;
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

    @Override
    @NonNull
    public ApplicationMenuItemList getItems() {
        return items;
    }

    @Nullable
    public Object getCustomData() {
        return customData;
    }

    public void setCustomData(@Nullable Object customData) {
        this.customData = customData;
    }

    public ApplicationMenuItem getAdministration() {
        return getMenuItem(DefaultMenuNames.Application.Main.Administration);
    }

    public ApplicationMenuItem getMenuItem(
            String menuItemName) {
        ApplicationMenuItem menuItem = getMenuItemOrNull(menuItemName);
        if (menuItem == null) {
            throw new AbpException("Could not find a menu item with given name: " + menuItemName);
        }

        return menuItem;
    }

    public ApplicationMenuItem getMenuItemOrNull(
            String menuItemName) {

        return getItems().stream().filter(mi -> mi.getName().equals(menuItemName)).findFirst().orElse(null);
    }

    public boolean tryRemoveMenuItem(String menuItemName) {
        return getItems().removeIf(item -> item.getName().equals(menuItemName));
    }

    public IHasMenuItems setSubItemOrder(String menuItemName, int order) {
        ApplicationMenuItem menuItem = getMenuItemOrNull(menuItemName);
        if (menuItem != null) {
            menuItem.setOrder(order);
        }

        return this;
    }

    @Override
    public String toString() {
        return "[ApplicationMenu] Name = " + name;
    }
}
