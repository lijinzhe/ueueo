package com.ueueo.web.multitenancy;

import com.ueueo.multitenancy.ITenantResolveContext;
import org.springframework.lang.NonNull;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * TODO by Lee on 2022-06-29 22:49 如果是post请求，读取body中的form数据，可能会因为数据流被读取而导致后面读取数据失败的问题，这块需要确认一下是否有问题？
 * 暂时先不要使用
 *
 * @author Lee
 * @date 2022-06-30 09:07
 */
@Deprecated
public class FormTenantResolveContributor extends HttpTenantResolveContributorBase {
    public final String ContributorName = "Form";

    private String tenantField;

    public FormTenantResolveContributor(String tenantField) {
        this.tenantField = tenantField;
    }

    @Override
    public String getName() {
        return ContributorName;
    }

    @Override
    protected String getTenantIdOrNameFromHttpContextOrNull(@NonNull ITenantResolveContext context, @NonNull ServletRequestAttributes requestAttributes) {
        return requestAttributes.getRequest().getParameter(tenantField);
    }

}
