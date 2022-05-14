package com.ueueo.multitenancy;

/**
 * @author Lee
 * @date 2022-05-13 22:17
 */
public interface ITenantStore {

    TenantConfiguration find(String name);

    TenantConfiguration find(Integer id);

}
