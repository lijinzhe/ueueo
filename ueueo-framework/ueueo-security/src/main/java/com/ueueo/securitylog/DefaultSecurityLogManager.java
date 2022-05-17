package com.ueueo.securitylog;

import lombok.Getter;

import java.util.function.Consumer;

/**
 * @author Lee
 * @date 2022-05-17 11:29
 */
public class DefaultSecurityLogManager implements ISecurityLogManager {

    @Getter
    protected AbpSecurityLogOptions securityLogOptions;
    @Getter
    protected ISecurityLogStore securityLogStore;

    public DefaultSecurityLogManager(AbpSecurityLogOptions securityLogOptions, ISecurityLogStore securityLogStore) {
        this.securityLogOptions = securityLogOptions;
        this.securityLogStore = securityLogStore;
    }

    @Override
    public void save(Consumer<SecurityLogInfo> saveAction) {
        if (securityLogOptions.isEnable()) {
            SecurityLogInfo securityLogInfo = create();
            saveAction.accept(securityLogInfo);
            securityLogStore.save(securityLogInfo);
        }
    }

    protected SecurityLogInfo create() {
        SecurityLogInfo securityLogInfo = new SecurityLogInfo();
        securityLogInfo.setApplicationName(securityLogOptions.getApplicationName());
        return securityLogInfo;
    }
}
