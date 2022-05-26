package com.ueueo.modularity;

import com.ueueo.ApplicationInitializationContext;
import com.ueueo.ApplicationShutdownContext;

/**
 *
 * @author Lee
 * @date 2021-08-24 14:16
 */
public class ModuleLifecycleContributorBase implements IModuleLifecycleContributor {
    @Override
    public void initialize(ApplicationInitializationContext context, IAbpModule module) {

    }

    @Override
    public void shutdown(ApplicationShutdownContext context, IAbpModule module) {

    }
}
