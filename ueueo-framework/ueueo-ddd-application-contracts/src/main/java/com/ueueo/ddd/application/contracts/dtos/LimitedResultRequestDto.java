package com.ueueo.ddd.application.contracts.dtos;

/** TODO ABP代码
 * Simply implements <see cref="ILimitedResultRequest"/>.
 *
 * @author Lee
 * @date 2021-08-29 09:52
 */
public class LimitedResultRequestDto implements ILimitedResultRequest {

    /** Default value: 10. */
    public static int DefaultMaxResultCount = 10;

    /**
     * Maximum possible value of the <see cref="MaxResultCount"/>.
     * Default value: 1,000.
     */
    public static int MaxMaxResultCount = 1000;

    /**
     * Maximum result count should be returned.
     * This is generally used to limit result count on paging.
     */
    private int maxResultCount = DefaultMaxResultCount;

    @Override
    public int getMaxResultCount() {
        return maxResultCount;
    }
    @Override
    public void setMaxResultCount(int maxResultCount) {
        this.maxResultCount = maxResultCount;
    }
}
