package com.ueueo.reflection;

import java.util.Collection;

/**
 * Used to get types in the application.
 * It may not return all types, but those are related with modules.
 *
 * @author Lee
 * @date 2022-05-29 13:19
 */
public interface ITypeFinder {
    Collection<Class<?>> getTypes();
}
