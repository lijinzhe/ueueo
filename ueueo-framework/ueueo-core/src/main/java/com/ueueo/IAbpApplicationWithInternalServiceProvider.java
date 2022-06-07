package com.ueueo;

import org.springframework.beans.factory.BeanFactory;

/**
 * @author Lee
 * @date 2022-06-07 21:43
 */
public interface IAbpApplicationWithInternalServiceProvider extends IAbpApplication {
    /**
     * Creates the service provider, but not initializes the modules.
     * Multiple calls returns the same service provider without creating again.
     */
    BeanFactory createBeanFactory();

    /**
     * Creates the service provider and initializes all the modules.
     * If <see cref="CreateServiceProvider"/> method was called before,
     * it does not re-create it, but uses the previous one.
     */
    void initialize();
}

