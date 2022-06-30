package com.ueueo.multitenancy;

import org.springframework.beans.factory.Aware;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-06-30 11:47
 */
public interface AbpTenantResolveOptionsAware extends Aware {

    void setOptions(AbpTenantResolveOptions options);
}
