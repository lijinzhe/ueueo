package com.ueueo.multitenancy;

import com.ueueo.data.AbpDatabaseInfo;
import com.ueueo.data.AbpDbConnectionOptions;
import com.ueueo.data.ConnectionStrings;
import com.ueueo.data.DefaultConnectionStringResolver;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Lee
 * @date 2022-05-14 17:31
 */
public class MultiTenantConnectionStringResolver extends DefaultConnectionStringResolver {

    private ICurrentTenant currentTenant;
    private ITenantStore tenantStore;

    public MultiTenantConnectionStringResolver(AbpDbConnectionOptions options,
                                               ICurrentTenant currentTenant,
                                               ITenantStore tenantStore) {
        super(options);
        this.currentTenant = currentTenant;
        this.tenantStore = tenantStore;
    }

    @Override
    public String resolve(String connectionStringName) {
        if (currentTenant.getId() == null) {
            //No current tenant, fallback to default logic
            return super.resolve(connectionStringName);
        }
        TenantConfiguration tenant = tenantStore.find(currentTenant.getId());
        if (tenant == null || MapUtils.isEmpty(tenant.getConnectionStrings())) {
            //Tenant has not defined any connection string, fallback to default logic
            return super.resolve(connectionStringName);
        }
        String tenantDefaultConnectionString = tenant.getConnectionStrings().getDefault();
        //Requesting default connection string...
        if (connectionStringName == null || connectionStringName.equals(ConnectionStrings.DefaultConnectionStringName)) {
            //Return tenant's default or global default
            return StringUtils.isNotBlank(tenantDefaultConnectionString)
                    ? tenantDefaultConnectionString
                    : options.getConnectionStrings().getDefault();
        }
        //Requesting specific connection string...
        String connString = tenant.getConnectionStrings().get(connectionStringName);
        if (StringUtils.isNotBlank(connString)) {
            //Found for the tenant
            return connString;
        }
        //Fallback to the mapped database for the specific connection string
        AbpDatabaseInfo database = options.getDatabases().getMappedDatabaseOrNull(connectionStringName);
        if (database != null && database.isUsedByTenants()) {
            connString = tenant.getConnectionStrings().get(database.getDatabaseName());
            if (StringUtils.isNotBlank(connString)) {
                //Found for the tenant
                return connString;
            }
        }
        //Fallback to tenant's default connection string if available
        if (StringUtils.isNotBlank(tenantDefaultConnectionString)) {
            return tenantDefaultConnectionString;
        }
        //Try to find the specific connection string for given name
        String connStringInOptions = options.getConnectionStrings().get(connectionStringName);
        if (StringUtils.isNotBlank(connStringInOptions)) {
            return connStringInOptions;
        }
        //Fallback to the global default connection string
        String defaultConnectionString = options.getConnectionStrings().getDefault();
        if (StringUtils.isNotBlank(defaultConnectionString)) {
            return defaultConnectionString;
        }
        return super.resolve(connectionStringName);
    }

}
