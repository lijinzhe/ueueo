package com.ueueo.dependencyinjection;

import org.springframework.beans.factory.BeanFactory;

/**
 * @author Lee
 * @date 2022-05-18 10:30
 */
public interface IServiceProviderAccessor {
    BeanFactory getServiceProvider();
}
