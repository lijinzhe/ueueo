package com.ueueo.data;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;

/**
 * @author Lee
 * @date 2022-05-16 11:37
 */
public class AbpDatabaseInfo {
    @Getter
    private String databaseName;
    /**
     * List of connection names mapped to this database.
     */
    @Getter
    private HashSet<String> mappedConnections;
    /**
     * Is this database used by tenants. Set this to false if this database can not owned by tenants.
     * Default: true.
     */
    @Getter
    @Setter
    private boolean isUsedByTenants = true;

    public AbpDatabaseInfo(String databaseName) {
        this.databaseName = databaseName;
        this.mappedConnections = new HashSet<>();
    }
}
