package com.ueueo.data;

import com.ueueo.exception.BaseException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Lee
 * @date 2022-05-16 11:36
 */
public class AbpDatabaseInfoDictionary extends HashMap<String, AbpDatabaseInfo> {
    private Map<String, AbpDatabaseInfo> connectionIndex;

    public AbpDatabaseInfoDictionary() {
        this.connectionIndex = new HashMap<>();
    }

    public AbpDatabaseInfo getMappedDatabaseOrNull(String connectionStringName) {
        return connectionIndex.get(connectionStringName);
    }

    public AbpDatabaseInfoDictionary configure(String databaseName, Consumer<AbpDatabaseInfo> configureAction) {
        AbpDatabaseInfo databaseInfo = this.computeIfAbsent(databaseName, AbpDatabaseInfo::new);
        configureAction.accept(databaseInfo);
        return this;
    }

    /**
     * This method should be called if this dictionary changes.
     * It refreshes indexes for quick access to the connection informations.
     */
    public void refreshIndexes() {
        connectionIndex = new HashMap<>();
        for (AbpDatabaseInfo databaseInfo : values()) {
            for (String mappedConnection : databaseInfo.getMappedConnections()) {
                if (connectionIndex.containsKey(mappedConnection)) {
                    throw new BaseException(String.format("A connection name can not map to multiple databases: %s.", mappedConnection));
                }
                connectionIndex.put(mappedConnection, databaseInfo);
            }
        }
    }
}
