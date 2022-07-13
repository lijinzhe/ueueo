package com.ueueo;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Lee
 * @date 2022-05-29 13:31
 */
public class NamedTypeSelectorListExtensions {

    /**
     * Add list of types to the list.
     *
     * @param list  List of NamedTypeSelector items
     * @param name  An arbitrary but unique name (can be later used to remove types from the list)
     * @param types
     */
    public static void add(List<NamedTypeSelector> list, String name, Class<?>[] types) {
        Objects.requireNonNull(list);
        Objects.requireNonNull(name);
        Objects.requireNonNull(types);

        list.add(new NamedTypeSelector(name, type -> Arrays.stream(types).anyMatch(type::isAssignableFrom)));
    }
}
