package com.ueueo;

import org.springframework.lang.NonNull;

/**
 * @author Lee
 * @date 2022-05-29 13:30
 */
public interface IOnApplicationShutdown {
    void onApplicationShutdownAsync(@NonNull ApplicationShutdownContext context);

    void onApplicationShutdown(@NonNull ApplicationShutdownContext context);
}
