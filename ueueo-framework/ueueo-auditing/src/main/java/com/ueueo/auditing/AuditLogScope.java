package com.ueueo.auditing;

import lombok.Getter;

/**
 * @author Lee
 * @date 2022-05-26 11:32
 */
@Getter
public class AuditLogScope implements IAuditLogScope {

    private final AuditLogInfo log;

    public AuditLogScope(AuditLogInfo log) {
        this.log = log;
    }
}
