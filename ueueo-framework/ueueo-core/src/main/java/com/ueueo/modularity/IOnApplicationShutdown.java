package com.ueueo.modularity;

import com.ueueo.ApplicationShutdownContext;

/**
 * @author Lee
 * @date 2021-08-20 10:41
 */
public interface IOnApplicationShutdown {

    void onApplicationShutdown(ApplicationShutdownContext context);
}
