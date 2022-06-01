package com.ueueo.uow;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Lee
 * @date 2022-05-19 20:35
 */
@Getter
@Setter
public class AbpUnitOfWorkOptions implements IAbpUnitOfWorkOptions {
    /**
     * Default: false.
     */
    private boolean isTransactional = false;
    private IsolationLevel isolationLevel;
    /**
     * Milliseconds
     */
    private Integer timeout;

    public AbpUnitOfWorkOptions() {}

    public AbpUnitOfWorkOptions(Boolean isTransactional, IsolationLevel isolationLevel, Integer timeout) {
        this.isTransactional = isTransactional != null ? isTransactional : false;
        this.isolationLevel = isolationLevel;
        this.timeout = timeout;
    }

    @Override
    public AbpUnitOfWorkOptions clone() {
        return new AbpUnitOfWorkOptions(isTransactional, isolationLevel, timeout);
    }

}
