package com.ueueo.auditing;

import org.springframework.lang.NonNull;

/**
 * @author Lee
 * @date 2022-05-26 11:00
 */
public interface IAuditLogScope {
    @NonNull
    AuditLogInfo getLog();
}
