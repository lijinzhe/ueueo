package com.ueueo.ddd.application.contracts.services;

import com.ueueo.ddd.application.contracts.dtos.PagedAndSortedResultRequestDto;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-24 15:28
 */
public interface IReadOnlyAppService<TEntityDto> extends IReadOnlyAppServiceWithInputOutput<TEntityDto, TEntityDto, PagedAndSortedResultRequestDto> {

}
