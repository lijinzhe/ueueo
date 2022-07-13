package com.ueueo.web.multitenancy;

import com.ueueo.multitenancy.ITenantResolveResultAccessor;
import com.ueueo.multitenancy.TenantResolveResult;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class HttpContextTenantResolveResultAccessor implements ITenantResolveResultAccessor {

    public final String HttpContextItemName = "__AbpTenantResolveResult";

    @Nullable
    @Override
    public TenantResolveResult getResult() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            return (TenantResolveResult) requestAttributes.getAttribute(HttpContextItemName, RequestAttributes.SCOPE_REQUEST);
        }
        return null;
    }

    @Override
    public void setResult(@Nullable TenantResolveResult result) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            if (result != null) {
                requestAttributes.setAttribute(HttpContextItemName, result, RequestAttributes.SCOPE_REQUEST);
            } else {
                requestAttributes.removeAttribute(HttpContextItemName, RequestAttributes.SCOPE_REQUEST);
            }
        }
    }

}
