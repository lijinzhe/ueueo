package com.ueueo;

import lombok.Data;

import java.util.function.Function;

/**
 * Used to represent a named type selector.
 *
 * @author Lee
 * @date 2022-05-14 16:11
 */
@Data
public class NamedTypeSelector {
    /** Name of the selector. */
    private String name;
    /** Predicate */
    private Function<Class<?>, Boolean> predicate;

    public NamedTypeSelector(String name, Function<Class<?>, Boolean> predicate) {
        this.name = name;
        this.predicate = predicate;
    }
}
