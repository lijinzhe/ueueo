package com.ueueo.ui.navigation;

import com.ueueo.util.Check;
import com.ueueo.authorization.IAuthorizationService;
import com.ueueo.authorization.permissions.PermissionSimpleStateCheckerExtensions;
import com.ueueo.localization.IStringLocalizer;
import com.ueueo.simplestatechecking.ISimpleStateCheckerManager;
import com.ueueo.simplestatechecking.SimpleStateCheckerResult;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class MenuManager implements IMenuManager {
    protected AbpNavigationOptions options;
    protected ISimpleStateCheckerManager<ApplicationMenuItem> simpleStateCheckerManager;
    private IStringLocalizer stringLocalizer;
    private IAuthorizationService authorizationService;

    public MenuManager(
            AbpNavigationOptions options,
            ISimpleStateCheckerManager<ApplicationMenuItem> simpleStateCheckerManager,
            IStringLocalizer stringLocalizer,
            IAuthorizationService authorizationService) {
        this.options = options;
        this.simpleStateCheckerManager = simpleStateCheckerManager;
        this.stringLocalizer = stringLocalizer;
        this.authorizationService = authorizationService;
    }

    @Override
    public ApplicationMenu get(String name) {
        return getInternalAsync(name);
    }

    @Override
    public ApplicationMenu getMainMenu() {
        return get(options.getMainMenuNames());
    }

    protected ApplicationMenu get(List<String> menuNames) {
        if (CollectionUtils.isEmpty(menuNames)) {
            return new ApplicationMenu(StandardMenus.Main, null);
        }

        List<ApplicationMenu> menus = new ArrayList<>();

        for (String menuName : options.getMainMenuNames()) {
            menus.add(getInternalAsync(menuName));
        }

        return mergeMenus(menus);
    }

    protected ApplicationMenu mergeMenus(List<ApplicationMenu> menus) {
        Check.notNullOrEmpty(menus, "menus");

        if (menus.size() == 1) {
            return menus.get(0);
        }

        ApplicationMenu firstMenu = menus.get(0);

        for (int i = 1; i < menus.size(); i++) {
            ApplicationMenu currentMenu = menus.get(i);
            for (ApplicationMenuItem menuItem : currentMenu.getItems()) {
                firstMenu.addItem(menuItem);
            }
        }

        return firstMenu;
    }

    protected ApplicationMenu getInternalAsync(String name) {
        ApplicationMenu menu = new ApplicationMenu(name, null);

        MenuConfigurationContext context = new MenuConfigurationContext(menu, stringLocalizer, authorizationService);

        for (IMenuContributor contributor : options.getMenuContributors()) {
            contributor.configureMenu(context);
        }

        checkPermissions(menu);

        normalizeMenu(menu);

        return menu;
    }

    protected void checkPermissions(IHasMenuItems menuWithItems) {
        List<ApplicationMenuItem> allMenuItems = new ArrayList<>();
        getAllMenuItems(menuWithItems, allMenuItems);

        for (ApplicationMenuItem item : allMenuItems) {
            if (StringUtils.isNotBlank(item.requiredPermissionName)) {
                PermissionSimpleStateCheckerExtensions.requirePermissions(item, Collections.singletonList(item.getRequiredPermissionName()), null);
            }
        }

        List<ApplicationMenuItem> checkPermissionsMenuItems = allMenuItems.stream().filter(x -> CollectionUtils.isNotEmpty(x.getStateCheckers())).collect(Collectors.toList());

        if (!checkPermissionsMenuItems.isEmpty()) {
            HashSet<ApplicationMenuItem> toBeDeleted = new HashSet<>();
            SimpleStateCheckerResult<ApplicationMenuItem> result = simpleStateCheckerManager.isEnabled(checkPermissionsMenuItems);
            for (ApplicationMenuItem menu : checkPermissionsMenuItems) {
                if (!result.get(menu)) {
                    toBeDeleted.add(menu);
                }
            }

            removeMenus(menuWithItems, toBeDeleted);
        }
    }

    protected void getAllMenuItems(IHasMenuItems menuWithItems, List<ApplicationMenuItem> output) {
        for (ApplicationMenuItem item : menuWithItems.getItems()) {
            output.add(item);
            getAllMenuItems(item, output);
        }
    }

    protected void removeMenus(IHasMenuItems menuWithItems, HashSet<ApplicationMenuItem> toBeDeleted) {
        menuWithItems.getItems().removeIf(toBeDeleted::contains);

        for (ApplicationMenuItem item : menuWithItems.getItems()) {
            removeMenus(item, toBeDeleted);
        }
    }

    protected void normalizeMenu(IHasMenuItems menuWithItems) {
        for (ApplicationMenuItem item : menuWithItems.getItems()) {
            normalizeMenu(item);
        }

        menuWithItems.getItems().normalize();
    }
}
