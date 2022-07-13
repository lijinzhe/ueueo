package com.ueueo.uow;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.function.Supplier;

/**
 * @author Lee
 * @date 2022-05-19 20:46
 */
public interface ITransactionApiContainer {
    @Nullable
    ITransactionApi findTransactionApi(@NonNull String key);

    void addTransactionApi(@NonNull String key, @NonNull ITransactionApi api);

    @NonNull
    ITransactionApi getOrAddTransactionApi(@NonNull String key, @NonNull Supplier<ITransactionApi> factory);
}
