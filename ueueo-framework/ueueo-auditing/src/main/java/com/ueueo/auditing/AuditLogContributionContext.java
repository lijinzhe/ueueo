package com.ueueo.auditing;

import com.ueueo.dependencyinjection.system.IServiceProvider;
import lombok.Getter;

/**
 *
 * @author Lee
 * @date 2022-05-26 11:38
 */
public class AuditLogContributionContext {
    @Getter
    public final IServiceProvider serviceProvider;
    @Getter
    public final AuditLogInfo auditInfo;

    public AuditLogContributionContext(IServiceProvider serviceProvider, AuditLogInfo auditInfo) {
        this.serviceProvider = serviceProvider;
        this.auditInfo = auditInfo;
    }
}
