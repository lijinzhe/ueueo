package com.ueueo.tenant.management.application;

import com.ueueo.ID;
import com.ueueo.data.DataSeedContext;
import com.ueueo.data.IDataSeeder;
import com.ueueo.ddd.application.contracts.dtos.PagedResultDto;
import com.ueueo.eventbus.distributed.IDistributedEventBus;
import com.ueueo.multitenancy.TenantCreatedEto;
import com.ueueo.tenant.management.domain.ITenantManager;
import com.ueueo.tenant.management.domain.ITenantRepository;
import com.ueueo.tenant.management.domain.Tenant;
import com.ueueo.tenant.management.objectmapping.TenantObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
    public TenantDto get(ID id) {
        return TenantObjectMapper.INSTANCE.map(tenantRepository.getById(id, true));
    }

    @Override
    public PagedResultDto<TenantDto> getList(GetTenantsInput input) {
        if (StringUtils.isBlank(input.getSorting())) {
            input.setSorting(Tenant.Fields.name);
        }
        long count = tenantRepository.getCount(input.getFilter());
        List<Tenant> list = tenantRepository.getList(input.getSorting(), input.getMaxResultCount(), input.getSkipCount(), input.getFilter(), null);
        return new PagedResultDto<>(count, list.stream().map(TenantObjectMapper.INSTANCE::map).collect(Collectors.toList()));
    }

    @Override
    public TenantDto create(TenantCreateDto input) {
        Tenant tenant = tenantManager.create(input.getName());
        //TODO input.MapExtraPropertiesTo(tenant);
        tenantRepository.insert(tenant, true);

        distributedEventBus.publish(new TenantCreatedEto(tenant.getId(), tenant.getName(), new HashMap<String, String>() {{
            put("AdminEmail", input.getAdminEmailAddress());
            put("AdminPassword", input.getAdminPassword());
        }}), true);
        //TODO  using (CurrentTenant.Change(tenant.Id, tenant.Name))
        //TODO: Handle database creation?
        //TODO: Seeder might be triggered via event handler.
        dataSeeder.seed(
                new DataSeedContext(tenant.getId())
                        .withProperty("AdminEmail", input.getAdminEmailAddress())
                        .withProperty("AdminPassword", input.getAdminPassword())
        );
        return TenantObjectMapper.INSTANCE.map(tenant);
    }

    @Override
    public void deleteById(ID id) {
        Tenant tenant = this.tenantRepository.getById(id, false);
        if (tenant != null) {
            this.tenantRepository.delete(tenant, true);
        }
    }

    @Override
    public TenantDto update(ID id, TenantUpdateDto input) {
        Tenant tenant = this.tenantRepository.getById(id, false);
        if (tenant != null) {
            tenantManager.changeName(tenant, input.getName());
            //            tenant.SetConcurrencyStampIfNotNull(input.ConcurrencyStamp);
            //            input.MapExtraPropertiesTo(tenant);
            tenantRepository.update(tenant, true);
            return TenantObjectMapper.INSTANCE.map(tenant);
        }
        return null;
    }

    @Override
    public String getDefaultConnectionString(ID id) {
        Tenant tenant = this.tenantRepository.getById(id, false);
        if (tenant != null) {
            return tenant.findDefaultConnectionString();
        }
        return null;
    }

    @Override
    public void updateDefaultConnectionString(ID id, String defaultConnectionString) {
        Tenant tenant = this.tenantRepository.getById(id, false);
        if (tenant != null) {
            tenant.setDefaultConnectionString(defaultConnectionString);
            tenantRepository.update(tenant, true);
        }
    }

    @Override
    public void deleteDefaultConnectionString(ID id) {
        Tenant tenant = this.tenantRepository.getById(id, false);
        if (tenant != null) {
            tenant.removeDefaultConnectionString();
            tenantRepository.update(tenant, true);
        }
    }

}
