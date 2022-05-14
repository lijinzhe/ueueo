package com.ueueo.ddd.application.contracts.dtos;

import java.util.List;

/** TODO ABP代码
 * Implements {@link IPagedResult}
 *
 * @param <T> Type of the items in the {@link ListResultDto} list
 *
 * @author Lee
 * @date 2021-08-24 15:34
 */
public class PagedResultDto<T> extends ListResultDto<T> implements IPagedResult<T> {
    private long totalCount;

    @Override
    public long getTotalCount() {
        return totalCount;
    }

    @Override
    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public PagedResultDto() {}

    /**
     * Creates a new {@link PagedResultDto}
     * @param totalCount Total count of Items
     * @param items List of items in current page
     */
    public PagedResultDto(long totalCount, List<T> items) {
        super(items);
        this.totalCount = totalCount;
    }
}
