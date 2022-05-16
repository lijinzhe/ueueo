package com.ueueo.data;

import com.ueueo.data.threading.DataAsyncTaskExecutor;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.Future;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-16 11:32
 */
public class DefaultConnectionStringResolver implements IConnectionStringResolver {

    protected AbpDbConnectionOptions options;

    public DefaultConnectionStringResolver(AbpDbConnectionOptions options) {
        this.options = options;
    }

    @Override
    public String resolve(String connectionStringName) {
        if (connectionStringName == null) {
            return options.getConnectionStrings().getDefault();
        }
        String connectionString = options.getConnectionStringOrNull(connectionStringName);
        if (StringUtils.isNotBlank(connectionString)) {
            return connectionString;
        }
        return "";
    }

    @Override
    public Future<String> resolveAsync(String connectionStringName) {
        return DataAsyncTaskExecutor.INSTANCE.submit(() -> resolve(connectionStringName));
    }
}
