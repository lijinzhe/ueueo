package com.ueueo.multitenancy;

import com.ueueo.ID;

/**
 * @author Lee
 * @date 2022-05-13 22:17
 */
public interface ITenantStore {

    TenantConfiguration find(String name);

    TenantConfiguration find(ID id);

}
