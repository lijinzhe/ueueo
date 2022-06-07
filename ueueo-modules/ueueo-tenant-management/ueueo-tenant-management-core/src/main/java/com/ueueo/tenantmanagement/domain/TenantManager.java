package com.ueueo.tenantmanagement.domain;

import com.ueueo.AbpException;
import com.ueueo.ID;
import com.ueueo.ddd.domain.services.DomainService;
import com.ueueo.guids.IGuidGenerator;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

/**
 * @author Lee
 * @date 2022-05-20 14:49
 */
public class TenantManager extends DomainService implements ITenantManager {

    private final ITenantRepository tenantRepository;

    private final IGuidGenerator guidGenerator;

    public TenantManager(ITenantRepository tenantRepository,
                         IGuidGenerator guidGenerator) {
        this.tenantRepository = tenantRepository;
        this.guidGenerator = guidGenerator;
    }

    @Override
    public Tenant create(@NonNull String name) {
        Assert.notNull(name, "name must not null!");
        validateName(name, null);
        return new Tenant(guidGenerator.create(), name);
    }

    @Override
    public void changeName(Tenant tenant, String name) {
        Assert.notNull(tenant, "tenant must not null!");
        Assert.notNull(name, "name must not null!");

        validateName(name, tenant.getId());
        tenant.setName(name);
    }

    protected void validateName(String name, ID expectedId) {
        Tenant tenant = tenantRepository.findByName(name, false);
        if (tenant != null && !tenant.getId().equals(expectedId)) {
            //TODO: A domain exception would be better..?
            throw new AbpException("Duplicate tenancy name: " + name);
        }
    }
}
