package com.ueueo.auditing;

import org.springframework.lang.Nullable;

/**
 * @author Lee
 * @date 2022-05-26 10:59
 */
public interface IAuditingManager {
    @Nullable
    IAuditLogScope getCurrent();

    IAuditLogSaveHandle beginScope();
}
