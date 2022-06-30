package com.ueueo.data;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Lee
 * @date 2022-05-16 11:36
 */
@Data
public class AbpDbConnectionOptions {

    private ConnectionStrings connectionStrings;

    private AbpDatabaseInfoDictionary databases;

    public AbpDbConnectionOptions() {
        this.connectionStrings = new ConnectionStrings();
        this.databases = new AbpDatabaseInfoDictionary();
    }

    public String getConnectionStringOrNull(String connectionStringName) {
        return getConnectionStringOrNull(connectionStringName, true, true);
    }

    public String getConnectionStringOrNull(String connectionStringName,
                                            boolean fallbackToDatabaseMappings,
                                            boolean fallbackToDefault) {
        String connectionString = connectionStrings.getOrDefault(connectionStringName, null);
        if (StringUtils.isNotBlank(connectionString)) {
            return connectionString;
        }

        if (fallbackToDatabaseMappings) {
            AbpDatabaseInfo database = databases.getMappedDatabaseOrNull(connectionStringName);
            if (database != null) {
                connectionString = connectionStrings.getOrDefault(database.getDatabaseName(), null);
                if (StringUtils.isNotBlank(connectionString)) {
                    return connectionString;
                }
            }
        }

        if (fallbackToDefault) {
            connectionString = connectionStrings.getDefault();
            if (StringUtils.isNotBlank(connectionString)) {
                return connectionString;
            }
        }

        return null;
    }
}
