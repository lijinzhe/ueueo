package com.ueueo.ddd.application.contracts.services;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-20 13:52
 */
public interface ICreateAppServiceWithInputOutput<TGetOutputDto, TCreateInput> extends IApplicationService {

    TGetOutputDto create(TCreateInput input);
}
