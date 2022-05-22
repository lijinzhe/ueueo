package com.ueueo.ddd.application.contracts.services;

import com.ueueo.ddd.application.contracts.dtos.PagedAndSortedResultRequestDto;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-24 15:27
 */
public interface ICrudAppService<TEntityDto>
        extends ICrudAppServiceWithInputOutput<TEntityDto, TEntityDto, PagedAndSortedResultRequestDto, TEntityDto, TEntityDto> {

}
