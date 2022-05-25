package com.ueueo.ddd.application.contracts.services;

/**
 *
 * @author Lee
 * @date 2021-08-24 15:27
 */
public interface ICrudAppServiceWithInputOutput<TGetOutputDto, TGetListOutputDto, TGetListInput, TCreateInput, TUpdateInput>
        extends IReadOnlyAppServiceWithInputOutput<TGetOutputDto, TGetListOutputDto, TGetListInput>,
        ICreateUpdateAppServiceWithInputOutput<TGetOutputDto, TCreateInput, TUpdateInput>,
        IDeleteAppService {

}
