package com.ueueo.ddd.application.contracts.dtos;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-29 09:56
 */
public class PagedResultRequestDto extends LimitedResultRequestDto implements IPagedResultRequest{
    @Override
    public int getSkipCount() {
        return 0;
    }

    @Override
    public void setSkipCount(int skipCount) {

    }
}
