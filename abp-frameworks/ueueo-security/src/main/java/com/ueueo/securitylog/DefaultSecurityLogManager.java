package com.ueueo.securitylog;

import lombok.Getter;

import java.util.function.Consumer;

/**
 * @author Lee
 * @date 2022-05-17 11:29
 */
public class DefaultSecurityLogManager implements ISecurityLogManager {

    /**
     * Default: true.
     */
    private boolean isEnable = true;
    /**
     * The name of the application or service writing security log.
     * Default: null.
     */
    private String applicationName;

    @Getter
    protected ISecurityLogStore securityLogStore;

    public DefaultSecurityLogManager(ISecurityLogStore securityLogStore) {
        this.securityLogStore = securityLogStore;
    }

    @Override
    public void save(Consumer<SecurityLogInfo> saveAction) {
        if (isEnable) {
            SecurityLogInfo securityLogInfo = create();
            saveAction.accept(securityLogInfo);
            securityLogStore.save(securityLogInfo);
        }
    }

    protected SecurityLogInfo create() {
        SecurityLogInfo securityLogInfo = new SecurityLogInfo();
        securityLogInfo.setApplicationName(applicationName);
        return securityLogInfo;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

}
