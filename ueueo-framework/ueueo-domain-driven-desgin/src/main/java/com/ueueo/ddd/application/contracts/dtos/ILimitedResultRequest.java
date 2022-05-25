package com.ueueo.ddd.application.contracts.dtos;

/**
 * This interface is defined to standardize to request a limited result.
 *
 * @author Lee
 * @date 2021-08-29 09:49
 */
public interface ILimitedResultRequest {
    /**
     * Maximum result count should be returned.
     * This is generally used to limit result count on paging.
     *
     * @return
     */
    int getMaxResultCount();

    void setMaxResultCount(int maxResultCount);
}
