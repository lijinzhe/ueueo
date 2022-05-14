package com.ueueo.multitenancy;

/**
 *
 * @author Lee
 * @date 2022-05-13 21:28
 */
public interface ITenantResolveContext {
    String getTenantIdOrName();

    void setTenantIdOrName(String tenantIdOrName);

    Boolean handled();

    void setHandled(Boolean handled);
}
