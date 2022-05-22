package com.ueueo.ddd.application.contracts.services;

import com.ueueo.ID;
import com.ueueo.ddd.application.contracts.dtos.PagedResultDto;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-24 15:28
 */
public interface IReadOnlyAppServiceWithInputOutput<TGetOutputDto, TGetListOutputDto, TGetListInput> extends IApplicationService {
    TGetOutputDto get(ID id);

    PagedResultDto<TGetListOutputDto> getList(TGetListInput input);
}
