package com.ueueo.uow;

public interface IAmbientUnitOfWork extends IUnitOfWorkAccessor {
    IUnitOfWork getCurrentByChecking();
}
