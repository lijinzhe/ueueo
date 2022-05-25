package com.ueueo.ddd.application.contracts.dtos;

/**
 * This interface is defined to standardize to request a sorted result.
 *
 * @author Lee
 * @date 2021-08-29 09:51
 */
public interface ISortedResultRequest {

    /**
     * Sorting information.
     * Should include sorting field and optionally a direction (ASC or DESC)
     * Can contain more than one field separated by comma (,).
     *
     * Examples:
     * "Name"
     * "Name DESC"
     * "Name ASC, Age DESC"
     *
     * @return
     */
    String getSorting();

    void setSorting(String sorting);
}
