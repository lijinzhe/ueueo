package com.ueueo;

import org.springframework.lang.NonNull;

/**
 * @author Lee
 * @date 2022-05-29 13:29
 */
public interface IOnApplicationInitialization {
    void onApplicationInitializationAsync(@NonNull ApplicationInitializationContext context);

    void onApplicationInitialization(@NonNull ApplicationInitializationContext context);
}
