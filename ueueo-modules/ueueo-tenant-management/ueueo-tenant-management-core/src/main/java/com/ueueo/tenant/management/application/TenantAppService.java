package com.ueueo.tenant.management.application;

import com.ueueo.ID;
import com.ueueo.data.IDataSeeder;
import com.ueueo.ddd.application.contracts.dtos.PagedResultDto;
import com.ueueo.eventbus.distributed.IDistributedEventBus;
import com.ueueo.tenant.management.domain.ITenantManager;
import com.ueueo.tenant.management.domain.ITenantRepository;
import com.ueueo.tenant.management.objectmapping.TenantObjectMapper;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-20 16:06
 */
public class TenantAppService extends TenantManagementAppServiceBase implements ITenantAppService {

    private IDataSeeder dataSeeder;
    private ITenantRepository tenantRepository;
    private ITenantManager tenantManager;
    private IDistributedEventBus distributedEventBus;

    public TenantAppService(IDataSeeder dataSeeder, ITenantRepository tenantRepository, ITenantManager tenantManager, IDistributedEventBus distributedEventBus) {
        this.dataSeeder = dataSeeder;
        this.tenantRepository = tenantRepository;
        this.tenantManager = tenantManager;
        this.distributedEventBus = distributedEventBus;
    }

    @Override
    public TenantDto create(TenantCreateDto tenantCreateDto) {
        return null;
    }

    @Override
    public void deleteById(ID id) {

    }

    @Override
    public TenantDto get(ID id) {
        return TenantObjectMapper.INSTANCE.map(tenantRepository.findById(id, true));
    }

    @Override
    public PagedResultDto<TenantDto> getList(GetTenantsInput getTenantsInput) {
        return null;
    }

    @Override
    public TenantDto update(ID id, TenantUpdateDto tenantUpdateDto) {
        return null;
    }

    @Override
    public String getDefaultConnectionString(ID id) {
        return null;
    }

    @Override
    public void updateDefaultConnectionString(ID id, String defaultConnectionString) {

    }

    @Override
    public void deleteDefaultConnectionString(ID id) {

    }

}
