package com.ueueo.multitenancy;

import org.springframework.beans.factory.Aware;

/**
 * @author Lee
 * @date 2022-06-30 11:47
 */
public interface AbpTenantResolveOptionsAware extends Aware {

    void setOptions(AbpTenantResolveOptions options);
}
