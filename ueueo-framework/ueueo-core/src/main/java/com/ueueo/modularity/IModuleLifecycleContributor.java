package com.ueueo.modularity;

import com.ueueo.ApplicationInitializationContext;
import com.ueueo.ApplicationShutdownContext;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-24 13:55
 */
public interface IModuleLifecycleContributor {
    void initialize(ApplicationInitializationContext context, IAbpModule module);

    void shutdown(ApplicationShutdownContext context, IAbpModule module);
}
