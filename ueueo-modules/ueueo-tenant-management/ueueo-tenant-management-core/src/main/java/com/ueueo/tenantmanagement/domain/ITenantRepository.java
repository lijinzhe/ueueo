package com.ueueo.tenantmanagement.domain;

import com.ueueo.ID;
import com.ueueo.ddd.domain.repositories.IBasicRepository;

import java.util.List;

/**
 * @author Lee
 * @date 2022-05-19 14:55
 */
public interface ITenantRepository extends IBasicRepository<Tenant, ID> {

    Tenant findByName(String name, Boolean includeDetails);

    /**
     * @param sorting
     * @param maxResultCount
     * @param skipCount
     * @param filter
     * @param includeDetails Default false
     *
     * @return
     */
    List<Tenant> getList(String sorting, int maxResultCount, int skipCount, String filter, Boolean includeDetails);

    long getCount(String filter);
}
