package com.ueueo.ddd.application.contracts.services;

import com.ueueo.ID;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-24 15:26
 */
public interface IUpdateAppServiceWithInputOutput<TGetOutputDto, TUpdateInput> extends IApplicationService {

    TGetOutputDto update(ID id, TUpdateInput input);

}
