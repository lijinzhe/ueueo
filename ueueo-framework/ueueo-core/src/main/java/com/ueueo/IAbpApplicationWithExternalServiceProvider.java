package com.ueueo;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.lang.NonNull;

/**
 * @author Lee
 * @date 2022-06-07 21:42
 */
public interface IAbpApplicationWithExternalServiceProvider extends IAbpApplication {
    /**
     * Sets the service provider, but not initializes the modules.
     */
    void setBeanFactory(@NonNull BeanFactory beanFactory);

    /**
     * Sets the service provider and initializes all the modules.
     * If <see cref="SetServiceProvider"/> was called before, the same
     * <see cref="serviceProvider"/> instance should be passed to this method.
     */
    void initialize(@NonNull BeanFactory beanFactory);
}
