package com.ueueo.securitylog;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Lee
 * @date 2022-05-17 10:48
 */
@Slf4j
public class SimpleSecurityLogStore implements ISecurityLogStore {

    @Getter
    protected AbpSecurityLogOptions securityLogOptions;

    public SimpleSecurityLogStore(AbpSecurityLogOptions securityLogOptions) {
        this.securityLogOptions = securityLogOptions;
    }

    @Override
    public void save(SecurityLogInfo securityLogInfo) {
        if (securityLogOptions.isEnable()) {
            log.info(securityLogInfo.toString());
        }
    }
}
