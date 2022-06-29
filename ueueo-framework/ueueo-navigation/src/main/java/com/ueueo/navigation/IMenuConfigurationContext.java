package com.ueueo.navigation;

import com.ueueo.authorization.IAbpAuthorizationService;
import com.ueueo.localization.IStringLocalizer;

public interface IMenuConfigurationContext {
    ApplicationMenu getMenu();

    IAbpAuthorizationService getAuthorizationService();

    IStringLocalizer getStringLocalizer();
}
