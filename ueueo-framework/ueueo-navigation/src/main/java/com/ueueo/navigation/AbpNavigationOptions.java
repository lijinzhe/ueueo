package com.ueueo.navigation;

import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lee
 * @date 2022-06-29 10:01
 */
public class AbpNavigationOptions {

    @NonNull
    public List<IMenuContributor> menuContributors;
    /**
     * Includes the <see cref="StandardMenus.Main"/> by default.
     */
    public List<String> mainMenuNames;

    @NonNull
    public List<IMenuContributor> getMenuContributors() {
        return menuContributors;
    }

    public List<String> getMainMenuNames() {
        return mainMenuNames;
    }

    public AbpNavigationOptions() {
        menuContributors = new ArrayList<>();
        mainMenuNames = new ArrayList<>();
        mainMenuNames.add(StandardMenus.Main);
    }
}
