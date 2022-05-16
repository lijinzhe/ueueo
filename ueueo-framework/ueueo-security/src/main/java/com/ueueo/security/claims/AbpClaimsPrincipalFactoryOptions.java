package com.ueueo.security.claims;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-16 22:29
 */
public class AbpClaimsPrincipalFactoryOptions {
    @Getter
    private List<Class<?>> contributors;

    public AbpClaimsPrincipalFactoryOptions() {
        this.contributors = new ArrayList<>();
    }
}
