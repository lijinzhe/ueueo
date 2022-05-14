package com.ueueo.ddd.application.contracts.services;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-24 15:25
 */
public interface ICreateUpdateAppService<TGetOutputDto, TKey, TCreateUpdateInput, TUpdateInput>
        extends ICreateAppService<TGetOutputDto, TCreateUpdateInput>, IUpdateAppService<TGetOutputDto, TKey, TUpdateInput> {

}
