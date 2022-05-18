package com.ueueo.securitylog;

import java.util.function.Consumer;

/**
 * @author Lee
 * @date 2022-05-17 11:28
 */
public interface ISecurityLogManager {
    void save(Consumer<SecurityLogInfo> saveAction);

}
