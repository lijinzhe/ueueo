package com.ueueo.modularity;

import com.ueueo.ApplicationInitializationContext;
import com.ueueo.ApplicationShutdownContext;

/**
 * @author Lee
 * @date 2022-05-27 09:30
 */
public class DefaultModuleLifecycleContributor {

    public static class OnApplicationInitializationModuleLifecycleContributor extends ModuleLifecycleContributorBase {
        @Override
        public void initialize(ApplicationInitializationContext context, IAbpModule module) {
            if (module instanceof IOnApplicationInitialization) {
                ((IOnApplicationInitialization) module).onApplicationInitialization(context);
            }
        }
    }

    public static class OnApplicationShutdownModuleLifecycleContributor extends ModuleLifecycleContributorBase {
        @Override
        public void shutdown(ApplicationShutdownContext context, IAbpModule module) {
            if (module instanceof IOnApplicationShutdown) {
                ((IOnApplicationShutdown) module).onApplicationShutdown(context);
            }
        }

    }

    public static class OnPreApplicationInitializationModuleLifecycleContributor extends ModuleLifecycleContributorBase {
        @Override
        public void initialize(ApplicationInitializationContext context, IAbpModule module) {
            if (module instanceof IOnPreApplicationInitialization) {
                ((IOnPreApplicationInitialization) module).onPreApplicationInitialization(context);
            }
        }

    }

    public static class OnPostApplicationInitializationModuleLifecycleContributor extends ModuleLifecycleContributorBase {
        @Override
        public void initialize(ApplicationInitializationContext context, IAbpModule module) {
            if (module instanceof IOnPostApplicationInitialization) {
                ((IOnPostApplicationInitialization) module).onPostApplicationInitialization(context);
            }
        }
    }
}
