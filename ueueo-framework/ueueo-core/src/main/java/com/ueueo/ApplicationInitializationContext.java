package com.ueueo;

import com.ueueo.dependencyinjection.IServiceProviderAccessor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-20 10:30
 */
public class ApplicationInitializationContext implements IServiceProviderAccessor {

    public BeanFactory serviceProvider;

    public ApplicationInitializationContext(@NonNull BeanFactory serviceProvider) {
        Objects.requireNonNull(serviceProvider);
        this.serviceProvider = serviceProvider;
    }

    @Override
    public BeanFactory getServiceProvider() {
        return serviceProvider;
    }
}
