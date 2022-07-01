package com.ueueo.clients;

import com.ueueo.ID;

/**
 * @author Lee
 * @date 2021-08-26 20:57
 */
public interface ICurrentClient {
    ID getId();

    boolean isAuthenticated();
}
