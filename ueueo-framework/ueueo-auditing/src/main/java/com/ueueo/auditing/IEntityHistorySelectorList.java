package com.ueueo.auditing;

import com.ueueo.NamedTypeSelector;

import java.util.Collection;

/**
 * @author Lee
 * @date 2022-05-26 11:33
 */
public interface IEntityHistorySelectorList extends Collection<NamedTypeSelector> {

    /**
     * Removes a selector by name.
     *
     * @param name
     *
     * @return
     */
    boolean removeByName(String name);
}
