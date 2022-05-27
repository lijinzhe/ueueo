package com.ueueo.auditing;

import com.ueueo.ID;
import com.ueueo.data.objectextending.ExtraPropertyDictionary;
import com.ueueo.data.objectextending.IHasExtraProperties;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Lee
 * @date 2022-05-26 11:01
 */
@Data
public class AuditLogInfo implements IHasExtraProperties, Serializable {
    private String applicationName;

    private ID userId;

    private String userName;

    private ID tenantId;

    private String tenantName;

    private ID impersonatorUserId;

    private ID impersonatorTenantId;

    private String impersonatorUserName;

    private String impersonatorTenantName;

    private Date executionTime;

    private long executionDuration;

    private String clientId;

    private String correlationId;

    private String clientIpAddress;

    private String clientName;

    private String browserInfo;

    private String httpMethod;

    private Integer httpStatusCode;

    private String url;

    private List<AuditLogActionInfo> actions;

    private List<Throwable> exceptions;

    private ExtraPropertyDictionary extraProperties;

    private List<EntityChangeInfo> entityChanges;

    private List<String> comments;

    public AuditLogInfo() {
        actions = new ArrayList<>();
        exceptions = new ArrayList<>();
        extraProperties = new ExtraPropertyDictionary();
        entityChanges = new ArrayList<>();
        comments = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("AUDIT LOG: [%s: %s] %s",
                httpStatusCode != null ? httpStatusCode.toString() : "---",
                httpMethod != null ? httpMethod : "-------",
                url)).append(StringUtils.LF);
        sb.append(String.format("- UserName - UserId                 : %s - %s", userName, userId)).append(StringUtils.LF);
        sb.append(String.format("- ClientIpAddress        : %s", clientIpAddress)).append(StringUtils.LF);
        sb.append(String.format("- ExecutionDuration      : %s", executionDuration)).append(StringUtils.LF);

        if (CollectionUtils.isNotEmpty(actions)) {
            sb.append("- Actions:").append(StringUtils.LF);
            for (AuditLogActionInfo action : actions) {
                sb.append(String.format("  - %s.%s (%s ms.)", action.getServiceName(), action.getMethodName(), action.getExecutionDuration())).append(StringUtils.LF);
                sb.append(String.format("    %s", action.getParameters())).append(StringUtils.LF);
            }
        }

        if (CollectionUtils.isNotEmpty(exceptions)) {
            sb.append("- Exceptions:").append(StringUtils.LF);
            for (Throwable exception : exceptions) {
                sb.append(String.format("  - %s", exception.getMessage())).append(StringUtils.LF);
                sb.append(String.format("    %s", exception)).append(StringUtils.LF);
            }
        }

        if (CollectionUtils.isNotEmpty(entityChanges)) {
            sb.append("- Entity Changes:").append(StringUtils.LF);
            for (EntityChangeInfo entityChange : entityChanges) {
                sb.append(String.format("  - [%s] %s, Id = %s", entityChange.getChangeType(), entityChange.getEntityTypeFullName(), entityChange.getEntityId()))
                        .append(StringUtils.LF);
                for (EntityPropertyChangeInfo propertyChange : entityChange.getPropertyChanges()) {
                    sb.append(String.format("    %s: %s -> %s", propertyChange.getPropertyName(), propertyChange.getOriginalValue(), propertyChange.getNewValue()))
                            .append(StringUtils.LF);
                }
            }
        }

        return sb.toString();
    }
}
