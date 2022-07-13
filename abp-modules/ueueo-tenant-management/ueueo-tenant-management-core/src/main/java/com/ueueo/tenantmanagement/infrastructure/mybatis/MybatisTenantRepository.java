package com.ueueo.tenantmanagement.infrastructure.mybatis;

import com.ueueo.ID;
import com.ueueo.tenantmanagement.domain.ITenantRepository;
import com.ueueo.tenantmanagement.domain.Tenant;

import java.util.Collection;
import java.util.List;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-25 21:25
 */
public class MybatisTenantRepository implements ITenantRepository {
    @Override
    public Tenant insert(Tenant entity, Boolean autoSave) {
        return null;
    }

    @Override
    public void insertMany(Collection<Tenant> tenants, Boolean autoSave) {

    }

    @Override
    public Tenant update(Tenant entity, Boolean autoSave) {
        return null;
    }

    @Override
    public void updateMany(Collection<Tenant> tenants, Boolean autoSave) {

    }

    @Override
    public void delete(Tenant entity, Boolean autoSave) {

    }

    @Override
    public void deleteById(ID id, Boolean autoSave) {

    }

    @Override
    public void deleteMany(Collection<Tenant> tenants, Boolean autoSave) {

    }

    @Override
    public void deleteManyByIds(Collection<ID> ids, Boolean autoSave) {

    }

    @Override
    public List<Tenant> getList(Boolean includeDetails) {
        return null;
    }

    @Override
    public long getCount() {
        return 0;
    }

    @Override
    public List<Tenant> getPagedList(int skipCount, int maxResultCount, String sorting, Boolean includeDetails) {
        return null;
    }

    @Override
    public Tenant getById(ID id, Boolean includeDetails) {
        return null;
    }

    @Override
    public Tenant findByName(String name, Boolean includeDetails) {
        return null;
    }

    @Override
    public List<Tenant> getList(String sorting, int maxResultCount, int skipCount, String filter, Boolean includeDetails) {
        return null;
    }

    @Override
    public long getCount(String filter) {
        return 0;
    }
}
