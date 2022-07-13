package com.ueueo.security.principal;

/**
 * TODO Description Of This JAVA Class.
 *
 * System.Security.Principal;
 * 系统类
 *
 * @author Lee
 * @date 2022-05-16 20:32
 */
public interface IIdentity {
    String getAuthenticationType();

    boolean getIsAuthenticated();

    String getName();
}
