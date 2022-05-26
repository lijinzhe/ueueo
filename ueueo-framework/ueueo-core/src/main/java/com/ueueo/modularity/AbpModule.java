package com.ueueo.modularity;

import com.ueueo.ApplicationInitializationContext;
import com.ueueo.ApplicationShutdownContext;
import com.weiming.framework.core.AbpException;

import java.util.function.Consumer;

/**
 * TODO ABP代码
 *
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

    @Override
    public void configureServices(ServiceConfigurationContext context) {

    }

    @Override
    public void onApplicationInitialization(ApplicationInitializationContext context) {

    }

    @Override
    public void onApplicationShutdown(ApplicationShutdownContext context) {

    }

    @Override
    public void onPostApplicationInitialization(ApplicationInitializationContext context) {

    }

    @Override
    public void onPreApplicationInitialization(ApplicationInitializationContext context) {

    }

    @Override
    public void postConfigureServices(ServiceConfigurationContext context) {

    }

    @Override
    public void preConfigureServices(ServiceConfigurationContext context) {

    }

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

    public static boolean isAbpModule(Class<?> type) {
        //        var typeInfo = type.GetTypeInfo();
        //
        //        return
        //                typeInfo.IsClass &&
        //                        !typeInfo.IsAbstract &&
        //                        !typeInfo.IsGenericType &&
        //                        typeof(IAbpModule).GetTypeInfo().IsAssignableFrom(type);
        //TODO by Lee on 2021-08-20 11:10
        return true;
    }

    protected static void checkAbpModuleType(Class<?> moduleType) {
        if (!isAbpModule(moduleType)) {
            throw new IllegalArgumentException("Given type is not an ABP module: " + moduleType.getTypeName());
        }
    }

    protected <TOptions> void configure(Consumer<TOptions> configureOptions) {
        //        serviceConfigurationContext.services.configure(configureOptions);
    }

    //    protected void Configure<TOptions>(Action<TOptions> configureOptions)
    //    where TOptions : class
    //    {
    //        ServiceConfigurationContext.Services.Configure(configureOptions);
    //    }
    //
    //    protected void Configure<TOptions>(string name, Action<TOptions> configureOptions)
    //    where TOptions : class
    //    {
    //        ServiceConfigurationContext.Services.Configure(name, configureOptions);
    //    }
    //
    //    protected void Configure<TOptions>(IConfiguration configuration)
    //    where TOptions : class
    //    {
    //        ServiceConfigurationContext.Services.Configure<TOptions>(configuration);
    //    }
    //
    //    protected void Configure<TOptions>(IConfiguration configuration, Action<BinderOptions> configureBinder)
    //    where TOptions : class
    //    {
    //        ServiceConfigurationContext.Services.Configure<TOptions>(configuration, configureBinder);
    //    }
    //
    //    protected void Configure<TOptions>(string name, IConfiguration configuration)
    //    where TOptions : class
    //    {
    //        ServiceConfigurationContext.Services.Configure<TOptions>(name, configuration);
    //    }
    //
    //    protected void PreConfigure<TOptions>(Action<TOptions> configureOptions)
    //    where TOptions : class
    //    {
    //        ServiceConfigurationContext.Services.PreConfigure(configureOptions);
    //    }
    //
    //    protected void PostConfigure<TOptions>(Action<TOptions> configureOptions)
    //    where TOptions : class
    //    {
    //        ServiceConfigurationContext.Services.PostConfigure(configureOptions);
    //    }
    //
    //    protected void PostConfigureAll<TOptions>(Action<TOptions> configureOptions)
    //    where TOptions : class
    //    {
    //        ServiceConfigurationContext.Services.PostConfigureAll(configureOptions);
    //    }
}
