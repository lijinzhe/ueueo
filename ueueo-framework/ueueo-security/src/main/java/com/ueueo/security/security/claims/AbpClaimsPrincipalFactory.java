package com.ueueo.security.security.claims;

import java.security.Principal;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 21:03
 */
public class AbpClaimsPrincipalFactory implements IAbpClaimsPrincipalFactory {
    public static final String AuthenticationType = "Abp.Application";

    public AbpClaimsPrincipalFactory() {

    }

    //TODO by Lee on 2021-08-26 21:05 未实现
    @Override
    public Principal create(Principal existsClaimsPrincipal) {
        return null;
    }
}
