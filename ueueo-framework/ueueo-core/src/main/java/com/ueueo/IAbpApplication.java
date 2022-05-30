package com.ueueo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Lee
 * @date 2022-05-27 09:59
 */
public interface IAbpApplication extends ApplicationContextAware {
    /**
     * Type of the startup (entrance) module of the application.
    */
    Class<?> getStartupModuleType();

    ApplicationContext getApplicationContext();

    /**
     * List of services registered to this application.
     * Can not add new services to this collection after application initialize.
    */
    //    IServiceCollection Services { get; }

    /**
     * Reference to the root service provider used by the application.
     * This can not be used before initialize the application.
    */
    //    IServiceProvider ServiceProvider { get; }

    /**
     * Calls the Pre/Post/ConfigureServicesAsync methods of the modules.
     * If you use this method, you must have set the <see cref="AbpApplicationCreationOptions.SkipConfigureServices"/>
     * option to true before.
    */
    //    Task ConfigureServicesAsync();

    /**
     * Used to gracefully shutdown the application and all modules.
    */
    //    Task ShutdownAsync();

    /**
     * Used to gracefully shutdown the application and all modules.
    */
    void shutdown();
}
