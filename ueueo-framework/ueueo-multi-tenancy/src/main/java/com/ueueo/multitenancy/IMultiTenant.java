package com.ueueo.multitenancy;

import com.ueueo.ID;

/**
 *
 * @author Lee
 * @date 2022-05-13 20:48
 */
public interface IMultiTenant {
    ID getTenantId();
}
