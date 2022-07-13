package com.ueueo.validation.stringvalues;

import lombok.Getter;

import java.util.Collection;

/**
 * @author Lee
 * @date 2022-05-29 16:53
 */
@Getter
public class StaticSelectionStringValueItemSource implements ISelectionStringValueItemSource {
    private Collection<ISelectionStringValueItem> items;

    public StaticSelectionStringValueItemSource(Collection<ISelectionStringValueItem> items) {
        this.items = items;
    }

}
