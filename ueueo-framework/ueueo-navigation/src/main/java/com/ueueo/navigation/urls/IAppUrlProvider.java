package com.ueueo.navigation.urls;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface IAppUrlProvider {
    String getUrl(@NonNull String appName, @Nullable String urlName);

    String getUrlOrNull(@NonNull String appName, @Nullable String urlName);

    boolean isRedirectAllowedUrl(String url);
}
