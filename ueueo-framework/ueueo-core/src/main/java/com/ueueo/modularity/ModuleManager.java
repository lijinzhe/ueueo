package com.ueueo.modularity;

import com.ueueo.AbpInitializationException;
import com.ueueo.AbpShutdownException;
import com.ueueo.ApplicationInitializationContext;
import com.ueueo.ApplicationShutdownContext;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-24 14:42
 */
public class ModuleManager implements IModuleManager {

    private IModuleContainer moduleContainer;
    private List<IModuleLifecycleContributor> lifecycleContributors;

    public ModuleManager(IModuleContainer moduleContainer, List<IModuleLifecycleContributor> lifecycleContributors) {
        this.moduleContainer = moduleContainer;
        this.lifecycleContributors = lifecycleContributors;
    }

    @Override
    public void initializeModules(ApplicationInitializationContext context) {
        for (IModuleLifecycleContributor contributor : lifecycleContributors) {
            for (IAbpModuleDescriptor module : moduleContainer.modules()) {
                try {
                    contributor.initialize(context, module.instance());
                } catch (Exception ex) {
                    throw new AbpInitializationException("An error occurred during the initialize {contributor.GetType().FullName} phase of the module {module.Type.AssemblyQualifiedName}: {ex.Message}. See the inner exception for details.", ex);
                }
            }
        }

        //        _logger.LogInformation("Initialized all ABP modules.");
    }

    @Override
    public void shutdownModules(ApplicationShutdownContext context) {
        List<IAbpModuleDescriptor> modules = moduleContainer.modules().stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());

        for (IModuleLifecycleContributor contributor : lifecycleContributors) {
            for (IAbpModuleDescriptor module : modules) {
                try {
                    contributor.shutdown(context, module.instance());
                } catch (Exception ex) {
                    throw new AbpShutdownException("An error occurred during the shutdown {contributor.GetType().FullName} phase of the module {module.Type.AssemblyQualifiedName}: {ex.Message}. See the inner exception for details.", ex);
                }
            }
        }
    }
}
