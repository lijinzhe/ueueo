package com.ueueo.multitenancy;

/**
 * @author Lee
 * @date 2022-07-05 20:07
 */
public interface MultiTenancyConfigurer {
    /**
     * A central point to enable/disable multi-tenancy.
     */
    boolean isEnabled();

    /**
     * Database style for tenants.
     */
    MultiTenancyDatabaseStyle databaseStyle();
}
