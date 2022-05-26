package com.ueueo.modularity;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-24 14:09
 */
public class AbpModuleHelper {
    public static List<Class<?>> findAllModuleTypes(Class<?> startupModuleType) {
        List<Class<?>> moduleTypes = new ArrayList<>();
        //        logger.Log(LogLevel.Information, "Loaded ABP modules:");
        addModuleAndDependenciesRecursively(moduleTypes, startupModuleType, 0);
        return moduleTypes;
    }

    public static List<Class<?>> findDependedModuleTypes(Class<?> moduleType) {
        AbpModule.checkAbpModuleType(moduleType);
        List<Class<?>> dependencies = new ArrayList<>();

        //TODO by Lee on 2021-08-24 14:14
        //        var dependencyDescriptors = moduleType
        //                .GetCustomAttributes()
        //                .OfType<IDependedTypesProvider>();
        //
        //        for (var descriptor in dependencyDescriptors)
        //        {
        //            foreach (var dependedModuleType in descriptor.GetDependedTypes())
        //            {
        //                dependencies.AddIfNotContains(dependedModuleType);
        //            }
        //        }
        return dependencies;
    }

    private static void addModuleAndDependenciesRecursively(List<Class<?>> moduleTypes, Class<?> moduleType, int depth) {
        AbpModule.checkAbpModuleType(moduleType);
        if (moduleTypes.contains(moduleType)) {
            return;
        }
        moduleTypes.add(moduleType);
        for (Class<?> dependedModuleType : findDependedModuleTypes(moduleType)) {
            addModuleAndDependenciesRecursively(moduleTypes, dependedModuleType, depth + 1);
        }
    }
}
