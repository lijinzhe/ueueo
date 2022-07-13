package com.ueueo.ui.navigation;

import com.ueueo.authorization.IAuthorizationService;
import com.ueueo.localization.IStringLocalizer;

public class MenuConfigurationContext implements IMenuConfigurationContext {

    private final IStringLocalizer stringLocalizer;

    private final IAuthorizationService authorizationService;

    private final ApplicationMenu menu;

    public MenuConfigurationContext(ApplicationMenu menu,
                                    IStringLocalizer stringLocalizer,
                                    IAuthorizationService authorizationService) {
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
    public IAuthorizationService getAuthorizationService() {
        return authorizationService;
    }

    @Override
    public IStringLocalizer getStringLocalizer() {
        return stringLocalizer;
    }
}
