package com.ueueo.web.multitenancy;

import com.ueueo.multitenancy.ITenantResolveContext;
import org.springframework.lang.NonNull;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 根据请求路径判断
 *
 * 例如：http://localhost:8080/api/abc/def/__tenantId/1/hig?a=b
 * 如果tenantField = __tenantId，则之后的1就是租户的ID或key
 *
 * @author Lee
 * @date 2022-06-30 09:13
 */
public class RouteTenantResolveContributor extends HttpTenantResolveContributorBase {

    public final String ContributorName = "Route";

    private final String tenantField;

    public RouteTenantResolveContributor(String tenantField) {
        this.tenantField = tenantField;
    }

    @Override
    public String getName() {
        return ContributorName;
    }

    @Override
    protected String getTenantIdOrNameFromHttpContextOrNull(@NonNull ITenantResolveContext context, @NonNull ServletRequestAttributes requestAttributes) {
        String uri = requestAttributes.getRequest().getRequestURI();
        String[] paths = uri.split("/");
        for (int i = 0; i < paths.length; i++) {
            String path = paths[i];
            if (tenantField.equals(path) && (i + 1) < paths.length) {
                return paths[i + 1];
            }
        }
        return null;
    }

}
