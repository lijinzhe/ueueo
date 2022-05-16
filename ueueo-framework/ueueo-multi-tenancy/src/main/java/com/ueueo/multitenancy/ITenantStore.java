package com.ueueo.multitenancy;

import java.util.concurrent.Future;

/**
 * @author Lee
 * @date 2022-05-13 22:17
 */
public interface ITenantStore {

    Future<TenantConfiguration> findAsync(String name);

    Future<TenantConfiguration> findAsync(Long id);

    TenantConfiguration find(String name);

    TenantConfiguration find(Long id);

}
