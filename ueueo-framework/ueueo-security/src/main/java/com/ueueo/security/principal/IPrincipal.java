package com.ueueo.security.principal;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-16 20:33
 */
public interface IPrincipal {
    IIdentity getIdentity();

    boolean isInRole(String role);
}
