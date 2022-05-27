package com.ueueo.internal;

import com.ueueo.AbpApplicationCreationOptions;
import com.ueueo.IAbpApplication;
import com.ueueo.modularity.ModuleLoader;
import org.springframework.context.ApplicationContext;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-27 10:45
 */
public class InternalServiceCollectionExtensions {

    public static void AddCoreServices(ApplicationContext services)
    {
        //TODO 程序初始化
//        services.AddOptions();
//        services.AddLogging();
//        services.AddLocalization();
    }

    public static void AddCoreAbpServices(ApplicationContext applicationContext,
                                            IAbpApplication abpApplication,
                                            AbpApplicationCreationOptions applicationCreationOptions)
    {
        //TODO 程序初始化
//        ModuleLoader moduleLoader = new ModuleLoader();
//        var assemblyFinder = new AssemblyFinder(abpApplication);
//        var typeFinder = new TypeFinder(assemblyFinder);
//
//        if (!services.IsAdded<IConfiguration>())
//        {
//            services.ReplaceConfiguration(
//                    ConfigurationHelper.BuildConfiguration(
//                            applicationCreationOptions.Configuration
//                    )
//            );
//        }
//
//        services.TryAddSingleton<IModuleLoader>(moduleLoader);
//        services.TryAddSingleton<IAssemblyFinder>(assemblyFinder);
//        services.TryAddSingleton<ITypeFinder>(typeFinder);
//        services.TryAddSingleton<IInitLoggerFactory>(new DefaultInitLoggerFactory());
//
//        services.AddAssemblyOf<IAbpApplication>();
//
//        services.AddTransient(typeof(ISimpleStateCheckerManager<>), typeof(SimpleStateCheckerManager<>));
//
//        services.Configure<AbpModuleLifecycleOptions>(options =>
//            {
//                    options.Contributors.Add<OnPreApplicationInitializationModuleLifecycleContributor>();
//        options.Contributors.Add<OnApplicationInitializationModuleLifecycleContributor>();
//        options.Contributors.Add<OnPostApplicationInitializationModuleLifecycleContributor>();
//        options.Contributors.Add<OnApplicationShutdownModuleLifecycleContributor>();
//        });
    }
}
