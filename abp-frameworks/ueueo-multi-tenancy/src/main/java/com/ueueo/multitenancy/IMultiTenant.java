package com.ueueo.multitenancy;

import com.ueueo.ID;

/**
 * 实现IMultiTenant接口，使当前类支持多租户
 *
 * @author Lee
 * @date 2022-05-13 20:48
 */
public interface IMultiTenant {
    /**
     * Id of the related tenant.
     *
     * @return tenant id
     */
    ID getTenantId();
}
