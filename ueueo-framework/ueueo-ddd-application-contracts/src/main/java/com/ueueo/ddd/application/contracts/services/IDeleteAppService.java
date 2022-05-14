package com.ueueo.ddd.application.contracts.services;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-24 15:28
 */
public interface IDeleteAppService<TKey> extends IApplicationService {
    void delete(TKey id);
}
