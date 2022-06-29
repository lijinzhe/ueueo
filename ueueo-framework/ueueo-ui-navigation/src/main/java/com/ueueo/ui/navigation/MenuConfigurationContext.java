package com.ueueo.ui.navigation;

import com.ueueo.authorization.IAbpAuthorizationService;
import com.ueueo.localization.IStringLocalizer;

public class MenuConfigurationContext implements IMenuConfigurationContext {

    private final IStringLocalizer stringLocalizer;

    private final IAbpAuthorizationService authorizationService;

    private final ApplicationMenu menu;

    public MenuConfigurationContext(ApplicationMenu menu,
                                    IStringLocalizer stringLocalizer,
                                    IAbpAuthorizationService authorizationService) {
        this.menu = menu;
        this.stringLocalizer = stringLocalizer;
        this.authorizationService = authorizationService;
    }

    public boolean isGranted(String policyName) {
        return authorizationService.isGranted(policyName);
    }

    @Override
    public ApplicationMenu getMenu() {
        return menu;
    }

    @Override
    public IAbpAuthorizationService getAuthorizationService() {
        return authorizationService;
    }

    @Override
    public IStringLocalizer getStringLocalizer() {
        return stringLocalizer;
    }
}
