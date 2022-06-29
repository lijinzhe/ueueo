package com.ueueo.ui.branding;

public class DefaultBrandingProvider implements IBrandingProvider {

    @Override
    public String getAppName() {
        return "UEUEO";
    }

    @Override
    public String getLogoUrl() {
        return null;
    }

    @Override
    public String getLogoReverseUrl() {
        return null;
    }
}
