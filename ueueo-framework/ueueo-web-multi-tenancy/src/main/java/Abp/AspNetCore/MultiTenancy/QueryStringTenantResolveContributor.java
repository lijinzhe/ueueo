package Abp.AspNetCore.MultiTenancy;

import com.ueueo.multitenancy.ITenantResolveContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.web.context.request.ServletRequestAttributes;

public class QueryStringTenantResolveContributor extends HttpTenantResolveContributorBase {

    public final String ContributorName = "QueryString";

    private AbpAspNetCoreMultiTenancyOptions options;

    public QueryStringTenantResolveContributor(AbpAspNetCoreMultiTenancyOptions options) {
        this.options = options;
    }

    @Override
    public String getName() {
        return ContributorName;
    }

    @Override
    protected String getTenantIdOrNameFromHttpContextOrNull(@NonNull ITenantResolveContext context, @NonNull ServletRequestAttributes httpContext) {
        if (StringUtils.isNotBlank(httpContext.getRequest().getQueryString())) {
            String tenantKey = options.getTenantKey();
            String[] queries = httpContext.getRequest().getQueryString().split("&");
            for (String query : queries) {
                String[] kv = query.split("=");
                if (kv.length == 2 && tenantKey.equals(kv[0])) {
                    String tenantValue = kv[1];
                    if (StringUtils.isBlank(tenantValue)) {
                        context.setHandled(true);
                        return null;
                    } else {
                        return tenantValue;
                    }
                }
            }
        }
        return null;
    }
}
