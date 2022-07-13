package com.ueueo.uow;

/**
 * @author Lee
 * @date 2022-05-19 20:35
 */
public interface IAbpUnitOfWorkOptions {

    boolean isTransactional();

    IsolationLevel getIsolationLevel();

    /**
     * Milliseconds
     *
     * @return
     */
    Integer getTimeout();
}
