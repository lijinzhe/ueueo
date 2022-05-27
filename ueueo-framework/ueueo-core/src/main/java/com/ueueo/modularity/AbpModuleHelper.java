package com.ueueo.modularity;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author Lee
 * @date 2021-08-24 14:09
 */
@Slf4j
public class AbpModuleHelper {
    public static List<Class<?>> findAllModuleTypes(Class<?> startupModuleType) {
        List<Class<?>> moduleTypes = new ArrayList<>();
        log.info("Loaded ABP modules:");
        addModuleAndDependenciesRecursively(moduleTypes, startupModuleType, 0);
        return moduleTypes;
    }

    public static List<Class<?>> findDependedModuleTypes(Class<?> moduleType) {
        AbpModule.checkAbpModuleType(moduleType);
        DependsOn dependsOn = moduleType.getAnnotation(DependsOn.class);
        Set<Class<?>> dependencies = new HashSet<>(Arrays.asList(dependsOn.dependedTypes()));
        return new ArrayList<>(dependencies);
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
