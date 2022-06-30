package com.ueueo.web.multitenancy;

import com.ueueo.multitenancy.ITenantResolveContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.web.context.request.ServletRequestAttributes;

public class QueryStringTenantResolveContributor extends HttpTenantResolveContributorBase {

    public static final String CONTRIBUTOR_NAME = "QueryString";

    private String tenantField;

    public QueryStringTenantResolveContributor(String tenantField) {
        this.tenantField = tenantField;
    }

    @Override
    public String getName() {
        return CONTRIBUTOR_NAME;
    }

    @Override
    protected String getTenantIdOrNameFromHttpContextOrNull(@NonNull ITenantResolveContext context, @NonNull ServletRequestAttributes httpContext) {
        if (StringUtils.isNotBlank(httpContext.getRequest().getQueryString())) {
            String[] queries = httpContext.getRequest().getQueryString().split("&");
            for (String query : queries) {
                String[] kv = query.split("=");
                if (kv.length == 2 && tenantField.equals(kv[0])) {
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
