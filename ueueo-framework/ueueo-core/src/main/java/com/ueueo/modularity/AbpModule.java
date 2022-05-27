package com.ueueo.modularity;

import com.ueueo.AbpException;
import com.ueueo.ApplicationInitializationContext;
import com.ueueo.ApplicationShutdownContext;

import java.lang.reflect.Modifier;
import java.util.function.Consumer;

/**
 * @author Lee
 * @date 2021-08-20 09:37
 */
public abstract class AbpModule implements IAbpModule,
        IOnPreApplicationInitialization,
        IOnApplicationInitialization,
        IOnPostApplicationInitialization,
        IOnApplicationShutdown,
        IPreConfigureServices,
        IPostConfigureServices {

    protected Boolean skipAutoServiceRegistration;

    private ServiceConfigurationContext serviceConfigurationContext;

    public Boolean getSkipAutoServiceRegistration() {
        return skipAutoServiceRegistration;
    }

    protected void setSkipAutoServiceRegistration(Boolean skipAutoServiceRegistration) {
        this.skipAutoServiceRegistration = skipAutoServiceRegistration;
    }

    public ServiceConfigurationContext getServiceConfigurationContext() {
        if (serviceConfigurationContext == null) {
            throw new AbpException("ServiceConfigurationContext is only available in the ConfigureServices, PreConfigureServices and PostConfigureServices methods.");
        }
        return serviceConfigurationContext;
    }

    public void setServiceConfigurationContext(ServiceConfigurationContext serviceConfigurationContext) {
        this.serviceConfigurationContext = serviceConfigurationContext;
    }

    @Override
    public void preConfigureServices(ServiceConfigurationContext context) {

    }

    @Override
    public void configureServices(ServiceConfigurationContext context) {

    }

    @Override
    public void postConfigureServices(ServiceConfigurationContext context) {

    }

    @Override
    public void onPreApplicationInitialization(ApplicationInitializationContext context) {

    }

    @Override
    public void onApplicationInitialization(ApplicationInitializationContext context) {

    }

    @Override
    public void onPostApplicationInitialization(ApplicationInitializationContext context) {

    }

    @Override
    public void onApplicationShutdown(ApplicationShutdownContext context) {

    }

    public static boolean isAbpModule(Class<?> type) {
        return IAbpModule.class.isAssignableFrom(type)
                && !Modifier.isAbstract(type.getModifiers());
    }

    protected static void checkAbpModuleType(Class<?> moduleType) {
        if (!isAbpModule(moduleType)) {
            throw new IllegalArgumentException("Given type is not an ABP module: " + moduleType.getTypeName());
        }

    }

    protected <TOptions> void configure(Class<TOptions> optionType, Consumer<TOptions> configureOptions) {
        TOptions options = serviceConfigurationContext.getApplicationContext().getBean(optionType);
        configureOptions.accept(options);
    }

}
