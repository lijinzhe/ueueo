package com.ueueo.ddd.application.contracts.services;

/**
 * @author Lee
 * @date 2022-05-20 13:52
 */
public interface ICreateAppServiceWithInputOutput<TGetOutputDto, TCreateInput> extends IApplicationService {

    TGetOutputDto create(TCreateInput input);
}
