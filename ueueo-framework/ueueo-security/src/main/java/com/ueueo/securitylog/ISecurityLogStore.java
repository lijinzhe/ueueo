package com.ueueo.securitylog;

import java.util.concurrent.Future;

/**
 * @author Lee
 * @date 2022-05-17 10:44
 */
public interface ISecurityLogStore {
    Future<?> saveAsync(SecurityLogInfo securityLogInfo);
}
