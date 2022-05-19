package com.ueueo.tenantmanagement.domain;

import com.ueueo.ddd.domain.services.IDomainService;
import org.springframework.lang.NonNull;

/**
 * @author Lee
 * @date 2022-05-18 15:14
 */
public interface ITenantManager extends IDomainService {
    @NonNull
    Tenant create(@NonNull String name);

    void changeName(@NonNull Tenant tenant, @NonNull String name);

}
