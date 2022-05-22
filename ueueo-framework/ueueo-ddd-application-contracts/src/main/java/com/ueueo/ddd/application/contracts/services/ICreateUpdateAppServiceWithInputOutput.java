package com.ueueo.ddd.application.contracts.services;

/**
 * @author Lee
 * @date 2021-08-24 15:25
 */
public interface ICreateUpdateAppServiceWithInputOutput<TGetOutputDto, TCreateUpdateInput, TUpdateInput>
        extends ICreateAppServiceWithInputOutput<TGetOutputDto, TCreateUpdateInput>, IUpdateAppServiceWithInputOutput<TGetOutputDto, TUpdateInput> {

}
