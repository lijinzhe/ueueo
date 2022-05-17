package com.ueueo.securitylog;

import com.ueueo.threading.CompletedFuture;
import lombok.Getter;

import java.util.concurrent.Future;
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
    public Future<?> saveAsync(Consumer<SecurityLogInfo> saveAction) {
        if (!securityLogOptions.isEnable()) {
            return new CompletedFuture<>();
        }
        SecurityLogInfo securityLogInfo = create();
        saveAction.accept(securityLogInfo);
        return securityLogStore.saveAsync(securityLogInfo);
    }

    protected SecurityLogInfo create() {
        SecurityLogInfo securityLogInfo = new SecurityLogInfo();
        securityLogInfo.setApplicationName(securityLogOptions.getApplicationName());
        return securityLogInfo;
    }
}
