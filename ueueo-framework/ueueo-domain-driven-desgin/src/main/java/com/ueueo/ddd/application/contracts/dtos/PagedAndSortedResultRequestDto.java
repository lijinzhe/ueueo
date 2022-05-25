package com.ueueo.ddd.application.contracts.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Simply implements <see cref="IPagedAndSortedResultRequest"/>.
 *
 * @author Lee
 * @date 2021-08-29 09:53
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PagedAndSortedResultRequestDto extends PagedResultRequestDto implements IPagedAndSortedResultRequest{

    private String sorting;

}
