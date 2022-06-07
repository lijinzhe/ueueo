package com.ueueo.localization;

import org.springframework.lang.NonNull;

import java.util.List;

public interface IInheritedResourceTypesProvider {
    @NonNull
    List<Class<?>> getInheritedResourceTypes();
}
