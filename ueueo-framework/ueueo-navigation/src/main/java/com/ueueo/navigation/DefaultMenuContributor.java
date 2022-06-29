package com.ueueo.navigation;

public class DefaultMenuContributor implements IMenuContributor {

    @Override
    public void configureMenu(MenuConfigurationContext context) {
        configure(context);
    }

    protected void configure(MenuConfigurationContext context) {

        if (context.getMenu().getName().equals(StandardMenus.Main)) {

            context.getMenu().addItem(
                    new ApplicationMenuItem(
                            DefaultMenuNames.Application.Main.Administration,
                            context.getStringLocalizer().get("Menu:Administration").getValue(),
                            null,
                            "fa fa-wrench", null, null, null, null, null, null
                    )
            );
        }
    }
}
