package com.ueueo.multitenancy;

/**
 * @author Lee
 * @date 2022-07-05 20:07
 */
public class MultiTenancyConfigurerSupport implements MultiTenancyConfigurer {
    /**
     * A central point to enable/disable multi-tenancy.
     * Default: false.
     */
    @Override
    public boolean isEnabled() {
        return false;
    }

    /**
     * Database style for tenants.
     * Default: {@link MultiTenancyDatabaseStyle#Hybrid}
     */
    @Override
    public MultiTenancyDatabaseStyle databaseStyle() {
        return MultiTenancyDatabaseStyle.Hybrid;
    }
}
