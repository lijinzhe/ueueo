package com.ueueo.ddd.application.contracts.services;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-24 15:24
 */
public interface ICreateAppService<TGetOutputDto, TCreateInput> extends IApplicationService {

    TGetOutputDto create(TCreateInput input);
}
