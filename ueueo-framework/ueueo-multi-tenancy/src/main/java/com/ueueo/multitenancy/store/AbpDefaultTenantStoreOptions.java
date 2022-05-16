package com.ueueo.multitenancy.store;

import com.ueueo.multitenancy.TenantConfiguration;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lee
 * @date 2022-05-16 20:20
 */
@Data
public class AbpDefaultTenantStoreOptions {

    private List<TenantConfiguration> tenants;

    public AbpDefaultTenantStoreOptions() {
        this.tenants = new ArrayList<>();
    }
}
