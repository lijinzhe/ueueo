package com.ueueo.modularity;

import com.ueueo.ApplicationInitializationContext;
import com.ueueo.ApplicationShutdownContext;

/**
 *
 * @author Lee
 * @date 2021-08-24 14:15
 */
public interface IModuleManager {
    void initializeModules(ApplicationInitializationContext context);

    void shutdownModules(ApplicationShutdownContext context);
}
