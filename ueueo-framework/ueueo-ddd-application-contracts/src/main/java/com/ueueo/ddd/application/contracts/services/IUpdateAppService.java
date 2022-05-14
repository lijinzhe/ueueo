package com.ueueo.ddd.application.contracts.services;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-24 15:26
 */
public interface IUpdateAppService<TGetOutputDto, TKey, TUpdateInput> extends IApplicationService {

    TGetOutputDto update(TKey id, TUpdateInput input);

}
