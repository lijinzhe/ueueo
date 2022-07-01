package com.ueueo.securitylog;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Lee
 * @date 2022-05-17 10:48
 */
@Slf4j
public class SimpleSecurityLogStore implements ISecurityLogStore {

    @Override
    public void save(SecurityLogInfo securityLogInfo) {
        log.info(securityLogInfo.toString());
    }
}
