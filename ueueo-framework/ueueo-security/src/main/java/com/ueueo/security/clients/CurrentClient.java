package com.ueueo.security.clients;

import com.ueueo.security.security.claims.ICurrentPrincipalAccessor;

import java.util.Optional;

/**
 * TODO ABP代码
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 20:58
 */
public class CurrentClient implements ICurrentClient {

    private ICurrentPrincipalAccessor principalAccessor;

    //TODO by Lee on 2021-08-26 20:59 未实现
    @Override
    public String getId() {
        return null;
    }

    @Override
    public boolean getIsAuthenticated() {
        return false;
    }

}
