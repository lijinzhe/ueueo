package com.ueueo.ddd.application.contracts.dtos;

/**
 * TODO ABP代码
 * This interface is defined to standardize to set "Total Count of Items" to a DTO.
 *
 * @author Lee
 * @date 2021-08-24 15:37
 */
public interface IHasTotalCount {
    /**
     * Total count of Items.
     *
     * @return
     */
    long getTotalCount();

    void setTotalCount(long totalCount);
}
