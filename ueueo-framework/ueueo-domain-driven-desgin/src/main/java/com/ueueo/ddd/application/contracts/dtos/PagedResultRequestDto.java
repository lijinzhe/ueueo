package com.ueueo.ddd.application.contracts.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Lee
 * @date 2021-08-29 09:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PagedResultRequestDto extends LimitedResultRequestDto implements IPagedResultRequest {

    private int skipCount;

}
