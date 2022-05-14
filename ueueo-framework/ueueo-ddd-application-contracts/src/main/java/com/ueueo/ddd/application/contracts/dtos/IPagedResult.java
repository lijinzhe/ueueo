package com.ueueo.ddd.application.contracts.dtos;

/**
 * TODO ABP代码
 * This interface is defined to standardize to return a page of items to clients.
 *
 * @param <T> Type of the items in the list
 *
 * @author Lee
 * @date 2021-08-24 15:35
 */
public interface IPagedResult<T> extends IListResult<T>, IHasTotalCount {

}
