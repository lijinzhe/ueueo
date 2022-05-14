package com.ueueo.ddd.application.contracts.dtos;

/**
 * TODO ABP代码
 * This interface is defined to standardize to request a paged result.
 *
 * @author Lee
 * @date 2021-08-29 09:50
 */
public interface IPagedResultRequest extends ILimitedResultRequest{
    /**
     * Skip count (beginning of the page).
     * @return
     */
    int getSkipCount();

    void setSkipCount(int skipCount);
}
