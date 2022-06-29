package com.ueueo.ui.navigation.urls;

import com.ueueo.AbpException;
import com.ueueo.multitenancy.ICurrentTenant;
import com.ueueo.multitenancy.ITenantStore;
import com.ueueo.multitenancy.TenantConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Slf4j
public class AppUrlProvider implements IAppUrlProvider {
    public final String TenantIdPlaceHolder = "{{tenantId}}";
    public final String TenantNamePlaceHolder = "{{tenantName}}";

    protected AppUrlOptions options;
    protected ICurrentTenant currentTenant;
    protected ITenantStore tenantStore;

    public AppUrlProvider(
            AppUrlOptions options,
            ICurrentTenant currentTenant,
            ITenantStore tenantStore) {
        this.currentTenant = currentTenant;
        this.tenantStore = tenantStore;
        this.options = options;
    }

    @Override
    public String getUrl(@NonNull String appName, @Nullable String urlName) {
        return replacePlaceHolders(getConfiguredUrl(appName, urlName));
    }

    @Override
    public boolean isRedirectAllowedUrl(String url) {
        boolean allow = options.getRedirectAllowedUrls().stream().anyMatch(url::startsWith);
        if (!allow) {
            log.error("Invalid RedirectUrl: {}, Use AppUrlProvider to configure it!", url);
        }
        return allow;
    }

    protected String getConfiguredUrl(String appName, String urlName) {
        String url = getUrlOrNull(appName, urlName);
        if (StringUtils.isNotBlank(url)) {
            return url;
        }

        if (StringUtils.isNotBlank(urlName)) {
            throw new AbpException(
                    String.format("Url, named '%s', for the application '%s' was not configured. Use AppUrlOptions to configure it!", urlName, appName));
        }

        throw new AbpException(
                String.format("RootUrl for the application '%s' was not configured. Use AppUrlOptions to configure it!", appName)
        );
    }

    protected String replacePlaceHolders(String url) {
        url = url.replace(
                TenantIdPlaceHolder,
                currentTenant.getId() != null ? currentTenant.getId().toString() : ""
        );

        if (!url.contains(TenantNamePlaceHolder)) {
            return url;
        }

        String tenantNamePlaceHolder = TenantNamePlaceHolder;

        if (url.contains(TenantNamePlaceHolder + '.')) {
            tenantNamePlaceHolder = TenantNamePlaceHolder + '.';
        }

        if (url.contains(tenantNamePlaceHolder)) {
            if (currentTenant.getId() != null) {
                url = url.replace(tenantNamePlaceHolder, getCurrentTenantName() + ".");
            } else {
                url = url.replace(tenantNamePlaceHolder, "");
            }
        }

        return url;
    }

    private String getCurrentTenantName() {
        if (currentTenant.getId() != null && StringUtils.isBlank(currentTenant.getName())) {
            TenantConfiguration tenantConfiguration = tenantStore.find(currentTenant.getId());
            return tenantConfiguration.getName();
        }

        return currentTenant.getName();
    }

    @Override
    public String getUrlOrNull(@NonNull String appName, @Nullable String urlName) {
        ApplicationUrlInfo app = options.getApplications().get(appName);

        if (StringUtils.isBlank(urlName)) {
            return app.getRootUrl();
        }

        String url = app.getUrls().get(urlName);

        if (app.getRootUrl() == null) {
            return url;
        }
        if (app.getRootUrl().endsWith("/")) {
            return app.getRootUrl() + url;
        } else {
            return app.getRootUrl() + "/" + url;
        }
    }

}
