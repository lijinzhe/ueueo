package com.ueueo.tenantmanagement.domain;

import com.ueueo.ID;
import com.ueueo.data.ConnectionStrings;
import com.ueueo.ddd.domain.entities.auditing.FullAuditedAggregateRoot;
import com.ueueo.tenantmanagement.domain.shared.TenantConsts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lee
 * @date 2022-05-18 15:15
 */
public class Tenant extends FullAuditedAggregateRoot {

    private String name;

    private List<TenantConnectionString> connectionStrings;

    protected Tenant() {
        super();
    }

    protected Tenant(ID id, @NonNull String name) {
        super(id);
        this.name = name;
        this.connectionStrings = new ArrayList<>();
    }

    @Nullable
    public String findDefaultConnectionString() {
        return findConnectionString(ConnectionStrings.DefaultConnectionStringName);
    }

    @Nullable
    public String findConnectionString(String name) {
        return connectionStrings.stream().filter(c -> StringUtils.equals(c.getName(), name)).findFirst().map(TenantConnectionString::getValue).orElse(null);
    }

    public void setDefaultConnectionString(String connectionString) {
        setConnectionString(ConnectionStrings.DefaultConnectionStringName, connectionString);
    }

    public void setConnectionString(String name, String connectionString) {
        TenantConnectionString tenantConnectionString = connectionStrings.stream().filter(c -> StringUtils.equals(c.getName(), name)).findFirst().orElse(null);

        if (tenantConnectionString != null) {
            tenantConnectionString.setValue(connectionString);
        } else {
            connectionStrings.add(new TenantConnectionString(getId(), name, connectionString));
        }
    }

    public void removeDefaultConnectionString() {
        removeConnectionString(ConnectionStrings.DefaultConnectionStringName);
    }

    public void removeConnectionString(String name) {
        connectionStrings.stream().filter(c -> StringUtils.equals(c.getName(), name))
                .findFirst()
                .ifPresent(connectionStrings::remove);

    }

    protected void setName(@NonNull String name) {
        Assert.isTrue(StringUtils.isNotBlank(name), "name must not null!");
        Assert.isTrue(name.length() <= TenantConsts.MaxNameLength,
                String.format("name length must <= %s!", TenantConsts.MaxNameLength));
        this.name = name;
    }
}
