package com.ueueo.clients;

import com.ueueo.ID;

import java.security.Principal;

/**
 * @author Lee
 * @date 2021-08-26 20:57
 */
public interface ICurrentClient extends Principal {

    ID getId();

    boolean isAuthenticated();

    @Override
    default String getName() {
        return getId().getStringValue();
    }
}
