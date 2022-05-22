package com.ueueo.tenant.management.application;

import com.ueueo.ID;
import com.ueueo.ddd.application.contracts.services.ICrudAppServiceWithInputOutput;

/**
 * @author Lee
 * @date 2022-05-20 13:46
 */
public interface ITenantAppService extends ICrudAppServiceWithInputOutput<TenantDto, TenantDto, GetTenantsInput, TenantCreateDto, TenantUpdateDto> {

    String getDefaultConnectionString(ID id);

    void updateDefaultConnectionString(ID id, String defaultConnectionString);

    void deleteDefaultConnectionString(ID id);
}
