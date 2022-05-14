package com.ueueo.ddd.application.contracts.services;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-24 15:27
 */
public interface ICrudAppService<TGetOutputDto, TGetListOutputDto, TKey, TGetListInput, TCreateInput, TUpdateInput>
        extends IReadOnlyAppService<TGetOutputDto, TGetListOutputDto, TKey, TGetListInput>,
        ICreateUpdateAppService<TGetOutputDto, TKey, TCreateInput, TUpdateInput>,
        IDeleteAppService<TKey> {

}
