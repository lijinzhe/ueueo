package com.ueueo.modularity;

import com.ueueo.AbpInitializationException;
import com.ueueo.AbpShutdownException;
import com.ueueo.ApplicationInitializationContext;
import com.ueueo.ApplicationShutdownContext;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lee
 * @date 2021-08-24 14:42
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ModuleManager implements IModuleManager {

    private final IModuleContainer moduleContainer;
    private final List<IModuleLifecycleContributor> lifecycleContributors;

    public ModuleManager(IModuleContainer moduleContainer,
                         AbpModuleLifecycleOptions options,
                         BeanFactory beanFactory) {
        this.moduleContainer = moduleContainer;
        this.lifecycleContributors = options.getContributors().stream()
                .map(contributor -> (IModuleLifecycleContributor) beanFactory.getBean(contributor))
                .collect(Collectors.toList());
    }

    @Override
    public void initializeModules(ApplicationInitializationContext context) {
        for (IModuleLifecycleContributor contributor : lifecycleContributors) {
            for (IAbpModuleDescriptor module : moduleContainer.getModules()) {
                try {
                    contributor.initialize(context, module.getInstance());
                } catch (Exception ex) {
                    throw new AbpInitializationException("An error occurred during the initialize {contributor.GetType().FullName} phase of the module {module.Type.AssemblyQualifiedName}: {ex.Message}. See the inner exception for details.", ex);
                }
            }
        }
    }

    @Override
    public void shutdownModules(ApplicationShutdownContext context) {
        List<IAbpModuleDescriptor> modules = moduleContainer.getModules().stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());

        for (IModuleLifecycleContributor contributor : lifecycleContributors) {
            for (IAbpModuleDescriptor module : modules) {
                try {
                    contributor.shutdown(context, module.getInstance());
                } catch (Exception ex) {
                    throw new AbpShutdownException("An error occurred during the shutdown {contributor.GetType().FullName} phase of the module {module.Type.AssemblyQualifiedName}: {ex.Message}. See the inner exception for details.", ex);
                }
            }
        }
    }
}
