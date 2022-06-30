package com.ueueo.ui.branding;

public interface IBrandingProvider {

    String getAppName();

    /**
     * Logo on white background
     *
     * @return
     */
    String getLogoUrl();

    /**
     * Logo on dark background
     *
     * @return
     */
    String getLogoReverseUrl();
}
