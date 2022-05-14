package com.ueueo.ddd.application.contracts.dtos;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-29 09:53
 */
public class PagedAndSortedResultRequestDto extends PagedResultRequestDto implements IPagedAndSortedResultRequest{
    @Override
    public String getSorting() {
        return null;
    }

    @Override
    public void setSorting(String sorting) {

    }
}
