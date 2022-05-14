package com.ueueo.multitenancy;

import com.ueueo.core.BusinessException;

/**
 *
 * @author Lee
 * @date 2022-05-13 21:11
 */
public interface ITenantConfigurationProvider {
    /**
     * @param saveResolveResult 默认false
     *
     * @return
     */
    TenantConfiguration get(boolean saveResolveResult) throws BusinessException;
}
