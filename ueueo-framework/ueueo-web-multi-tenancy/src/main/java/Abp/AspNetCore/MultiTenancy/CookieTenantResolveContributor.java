package Abp.AspNetCore.MultiTenancy;

import com.ueueo.multitenancy.ITenantResolveContext;
import org.springframework.lang.NonNull;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;

public class CookieTenantResolveContributor extends HttpTenantResolveContributorBase {
    public final String ContributorName = "Cookie";

    private AbpAspNetCoreMultiTenancyOptions options;

    public CookieTenantResolveContributor(AbpAspNetCoreMultiTenancyOptions options) {
        this.options = options;
    }

    @Override
    public String getName() {
        return ContributorName;
    }

    @Override
    protected String getTenantIdOrNameFromHttpContextOrNull(@NonNull ITenantResolveContext context, @NonNull ServletRequestAttributes requestAttributes) {
        Cookie[] cookies = requestAttributes.getRequest().getCookies();
        for (Cookie cookie : cookies) {
            if (options.getTenantKey().equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
