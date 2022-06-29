package Abp.AspNetCore.MultiTenancy;

import com.ueueo.multitenancy.ITenantResolveContext;
import com.ueueo.multitenancy.TenantResolveContributorBase;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
public abstract class HttpTenantResolveContributorBase extends TenantResolveContributorBase {

    @Override
    public void resolve(ITenantResolveContext context) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }

        try {
            resolveFromHttpContext(context, requestAttributes);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
    }

    protected void resolveFromHttpContext(ITenantResolveContext context, ServletRequestAttributes requestAttributes) {
        String tenantIdOrName = getTenantIdOrNameFromHttpContextOrNull(context, requestAttributes);
        if (StringUtils.isNotBlank(tenantIdOrName)) {
            context.setTenantIdOrName(tenantIdOrName);
        }
    }

    protected abstract String getTenantIdOrNameFromHttpContextOrNull(@NonNull ITenantResolveContext context, @NonNull ServletRequestAttributes requestAttributes);
}
