package com.ueueo.securitylog;

import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * @author Lee
 * @date 2022-05-17 11:28
 */
public interface ISecurityLogManager {
    Future<?> saveAsync(Consumer<SecurityLogInfo> saveAction);
}
