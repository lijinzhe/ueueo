package com.ueueo.navigation;

import org.springframework.lang.Nullable;

public interface IHasMenuItems {

    /**
     * Menu items.
     *
     * @return
     */
    ApplicationMenuItemList getItems();

    @Nullable
    default ApplicationMenuItem findMenuItem(String menuItemName) {
        for (ApplicationMenuItem menuItem : getItems()) {
            if (menuItem.getName().equals(menuItemName)) {
                return menuItem;
            }

            ApplicationMenuItem subItem = findMenuItem(menuItemName);
            if (subItem != null) {
                return subItem;
            }
        }

        return null;
    }
}
