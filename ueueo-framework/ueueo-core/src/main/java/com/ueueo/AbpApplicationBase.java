package com.ueueo;

import com.ueueo.internal.InternalServiceCollectionExtensions;
import com.ueueo.modularity.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-27 10:26
 */
@Slf4j
public abstract class AbpApplicationBase implements IAbpApplication {

    @NonNull
    @Getter
    private final Class<?> StartupModuleType;
    @Getter
    private ApplicationContext applicationContext;

    private final DefaultListableBeanFactory beanFactory;
    @Getter
    private final List<IAbpModuleDescriptor> modules;

    private boolean configuredServices;

    AbpApplicationBase(
            @NonNull Class<?> startupModuleType,
            @NonNull ApplicationContext applicationContext,
            @Nullable Consumer<AbpApplicationCreationOptions> optionsAction) {
        Objects.requireNonNull(startupModuleType);
        Objects.requireNonNull(applicationContext);

        this.StartupModuleType = startupModuleType;
        this.applicationContext = applicationContext;

        AbpApplicationCreationOptions options = new AbpApplicationCreationOptions(applicationContext);
        if (optionsAction != null) {
            optionsAction.accept(options);
        }
        this.beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        this.beanFactory.registerSingleton(IAbpApplication.class.getName(), this);
        this.beanFactory.registerSingleton(IModuleContainer.class.getName(), this);

        InternalServiceCollectionExtensions.AddCoreServices(applicationContext);
        InternalServiceCollectionExtensions.AddCoreAbpServices(applicationContext, this, options);

        modules = loadModules(applicationContext, options);

        if (!options.isSkipConfigureServices()) {
            configureServices();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void shutdown() {
        applicationContext.getBean(IModuleManager.class).shutdownModules(new ApplicationShutdownContext(applicationContext));
    }

    public void dispose() {
        //TODO: Shutdown if not done before?
    }

    protected void initializeModules() {
        applicationContext.getBean(IModuleManager.class)
                .initializeModules(new ApplicationInitializationContext(applicationContext));
    }

    protected List<IAbpModuleDescriptor> loadModules(ApplicationContext applicationContext, AbpApplicationCreationOptions options) {
        return applicationContext.getBean(IModuleLoader.class).loadModules(applicationContext, StartupModuleType);
    }

    //TODO: We can extract a new class for this
    public void configureServices() {
        checkMultipleConfigureServices();

        ServiceConfigurationContext context = new ServiceConfigurationContext(applicationContext);
        beanFactory.registerSingleton(ServiceConfigurationContext.class.getName(), context);

        for (IAbpModuleDescriptor module : modules) {
            if (module.getInstance() instanceof AbpModule) {
                ((AbpModule) module.getInstance()).setServiceConfigurationContext(context);
            }
        }

        //PreConfigureServices
        for (IAbpModuleDescriptor module : modules.stream().filter(m -> m.getInstance() instanceof IPreConfigureServices).collect(Collectors.toList())) {
            try {
                ((IPreConfigureServices) module.getInstance()).preConfigureServices(context);
            } catch (Exception ex) {
                throw new AbpInitializationException(String.format("An error occurred during IPreConfigureServices.PreConfigureServicesAsync phase of the module %s. See the inner exception for details.",
                        module.getType().getName()), ex);
            }
        }

        //ConfigureServices
        for (IAbpModuleDescriptor module : modules) {
            try {
                module.getInstance().configureServices(context);
            } catch (Exception ex) {
                throw new AbpInitializationException(String.format("An error occurred during IAbpModule.ConfigureServicesAsync phase of the module %s. See the inner exception for details.",
                        module.getType().getName()), ex);
            }
        }

        //PostConfigureServices
        for (IAbpModuleDescriptor module : modules.stream().filter(m -> m.getInstance() instanceof IPostConfigureServices).collect(Collectors.toList())) {
            try {
                ((IPostConfigureServices) module.getInstance()).postConfigureServices(context);
            } catch (Exception ex) {
                throw new AbpInitializationException(String.format("An error occurred during IPostConfigureServices.PostConfigureServicesAsync phase of the module %s. See the inner exception for details.",
                        module.getType().getName()), ex);
            }
        }

        for (IAbpModuleDescriptor module : modules) {
            if (module.getInstance() instanceof AbpModule) {
                ((AbpModule) module.getInstance()).setServiceConfigurationContext(null);
            }
        }

        configuredServices = true;
    }

    private void checkMultipleConfigureServices() {
        if (configuredServices) {
            throw new AbpInitializationException("Services have already been configured! If you call ConfigureServicesAsync method, you must have set AbpApplicationCreationOptions.SkipConfigureServices tu true before.");
        }
    }

}
