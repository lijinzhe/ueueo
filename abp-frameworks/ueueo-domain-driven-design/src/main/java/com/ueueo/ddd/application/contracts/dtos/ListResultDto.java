package com.ueueo.ddd.application.contracts.dtos;

import com.ueueo.data.objectextending.ExtensibleObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lee
 * @date 2021-08-24 15:38
 */
public class ListResultDto<T> extends ExtensibleObject implements IListResult<T> {

    private List<T> items;

    @Override
    public List<T> getItems() {
        return new ArrayList<>(items);
    }

    @Override
    public void setItems(List<T> items) {
        this.items = items;
    }

    public ListResultDto() {}

    public ListResultDto(List<T> items) {
        this.items = items;
    }
}
