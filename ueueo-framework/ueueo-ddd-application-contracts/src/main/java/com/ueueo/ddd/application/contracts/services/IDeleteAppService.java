package com.ueueo.ddd.application.contracts.services;

import com.ueueo.ID;

/**
 * @author Lee
 * @date 2021-08-24 15:28
 */
public interface IDeleteAppService extends IApplicationService {
    void deleteById(ID id);
}
