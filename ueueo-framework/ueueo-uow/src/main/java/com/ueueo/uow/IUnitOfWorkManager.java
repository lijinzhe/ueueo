package com.ueueo.uow;

import org.springframework.lang.NonNull;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-19 20:32
 */
public interface IUnitOfWorkManager {
    IUnitOfWork getCurrent();

    IUnitOfWork begin(@NonNull AbpUnitOfWorkOptions options, Boolean requiresNew);

    @NonNull
    IUnitOfWork reserve(@NonNull String reservationName, Boolean requiresNew);

    void beginReserved(@NonNull String reservationName, @NonNull AbpUnitOfWorkOptions options);

    boolean tryBeginReserved(@NonNull String reservationName, @NonNull AbpUnitOfWorkOptions options);
}
