package Abp.AspNetCore.MultiTenancy;

import com.ueueo.multitenancy.ITenantResolveContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Slf4j
public class HeaderTenantResolveContributor extends HttpTenantResolveContributorBase {
    public final String ContributorName = "Header";

    private AbpAspNetCoreMultiTenancyOptions options;

    public HeaderTenantResolveContributor(AbpAspNetCoreMultiTenancyOptions options) {
        this.options = options;
    }

    @Override
    public String getName() {
        return ContributorName;
    }

    @Override
    protected String getTenantIdOrNameFromHttpContextOrNull(@NonNull ITenantResolveContext context, @NonNull ServletRequestAttributes requestAttributes) {
        Enumeration<String> tenantIdKey = requestAttributes.getRequest().getHeaders(options.getTenantKey());

        if (tenantIdKey == null || !tenantIdKey.hasMoreElements()) {
            return null;
        }
        List<String> tenantIdHeader = new ArrayList<>();
        while (tenantIdKey.hasMoreElements()) {
            tenantIdHeader.add(tenantIdKey.nextElement());
        }
        if (tenantIdHeader.size() > 1) {
            log.warn("HTTP request includes more than one {} header value. First one will be used. All of them: {}", tenantIdKey, StringUtils.join(tenantIdHeader, ", "));
        }
        return tenantIdHeader.get(0);
    }
}
