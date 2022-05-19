package com.ueueo.uow;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.function.Supplier;

/**
 * @author Lee
 * @date 2022-05-19 20:43
 */
public interface IDatabaseApiContainer {
    @Nullable
    IDatabaseApi findDatabaseApi(@NonNull String key);

    void addDatabaseApi(@NonNull String key, @NonNull IDatabaseApi api);

    @NonNull
    IDatabaseApi getOrAddDatabaseApi(@NonNull String key, @NonNull Supplier<IDatabaseApi> factory);
}
