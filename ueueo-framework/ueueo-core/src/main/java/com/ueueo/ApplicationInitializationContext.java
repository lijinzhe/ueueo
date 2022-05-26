package com.ueueo;

import com.ueueo.dependencyinjection.IServiceProviderAccessor;
import com.ueueo.dependencyinjection.system.IServiceProvider;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-20 10:30
 */
public class ApplicationInitializationContext implements IServiceProviderAccessor {

    public IServiceProvider serviceProvider;

    public ApplicationInitializationContext(@NonNull IServiceProvider serviceProvider) {
        Objects.requireNonNull(serviceProvider);
        this.serviceProvider = serviceProvider;
    }

    @Override
    public IServiceProvider getServiceProvider() {
        return serviceProvider;
    }
}
