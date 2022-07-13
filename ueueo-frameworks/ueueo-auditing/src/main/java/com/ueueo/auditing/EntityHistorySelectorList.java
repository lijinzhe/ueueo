package com.ueueo.auditing;

import com.ueueo.NamedTypeSelector;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Lee
 * @date 2022-05-26 11:41
 */
class EntityHistorySelectorList extends ArrayList<NamedTypeSelector> implements IEntityHistorySelectorList {
    @Override
    public boolean removeByName(String name) {
        return removeIf(s -> Objects.equals(s.getName(), name));
    }

}

