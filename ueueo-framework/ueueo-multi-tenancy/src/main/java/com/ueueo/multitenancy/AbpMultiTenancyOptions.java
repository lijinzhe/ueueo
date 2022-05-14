package com.ueueo.multitenancy;

import lombok.Data;

/**
 * @author Lee
 * @date 2022-05-14 17:28
 */
@Data
public class AbpMultiTenancyOptions {
    /**
     * A central point to enable/disable multi-tenancy.
     * Default: false.
     */
    private boolean isEnabled;
    /**
     * Database style for tenants.
     * Default: {@link MultiTenancyDatabaseStyle.Hybrid}
     */
    private MultiTenancyDatabaseStyle databaseStyle = MultiTenancyDatabaseStyle.Hybrid;
}
