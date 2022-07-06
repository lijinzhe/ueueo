package com.ueueo.ui.navigation;

import com.ueueo.authorization.IAuthorizationService;
import com.ueueo.localization.IStringLocalizer;

public interface IMenuConfigurationContext {
    ApplicationMenu getMenu();

    IAuthorizationService getAuthorizationService();

    IStringLocalizer getStringLocalizer();
}
