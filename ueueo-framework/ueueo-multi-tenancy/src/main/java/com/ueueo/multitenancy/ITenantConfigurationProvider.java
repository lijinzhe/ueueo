package com.ueueo.multitenancy;

import java.util.concurrent.Future;

/**
 * @author Lee
 * @date 2022-05-13 21:11
 */
public interface ITenantConfigurationProvider {
    /**
     * @param saveResolveResult 默认false
     *
     * @return
     */
    Future<TenantConfiguration> getAsync(boolean saveResolveResult);
}
