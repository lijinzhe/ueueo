package com.ueueo.auditing;

import lombok.Getter;

/**
 * @author Lee
 * @date 2022-05-26 11:38
 */
public class AuditLogContributionContext {
    @Getter
    public final AuditLogInfo auditInfo;

    public AuditLogContributionContext(AuditLogInfo auditInfo) {
        this.auditInfo = auditInfo;
    }
}
