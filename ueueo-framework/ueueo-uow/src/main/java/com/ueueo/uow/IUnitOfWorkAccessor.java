package com.ueueo.uow;

import org.springframework.lang.Nullable;

public interface IUnitOfWorkAccessor {
    @Nullable
    IUnitOfWork getUnitOfWork();

    void setUnitOfWork(@Nullable IUnitOfWork unitOfWork);
}
