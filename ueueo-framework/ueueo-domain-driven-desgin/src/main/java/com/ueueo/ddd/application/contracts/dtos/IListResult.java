package com.ueueo.ddd.application.contracts.dtos;

import java.util.List;

/**
 * This interface is defined to standardize to return a list of items to clients.
 *
 * @param <T> Type of the items in the list
 *
 * @author Lee
 * @date 2021-08-24 15:36
 */
public interface IListResult<T> {
    /**
     * List of items.
     *
     * @return
     */
    List<T> getItems();

    void setItems(List<T> items);
}
