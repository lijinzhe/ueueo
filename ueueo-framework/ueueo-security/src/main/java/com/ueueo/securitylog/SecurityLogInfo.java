package com.ueueo.securitylog;

import com.ueueo.ID;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Lee
 * @date 2022-05-17 10:45
 */
@Data
public class SecurityLogInfo {
    public String applicationName;

    public String identity;

    public String action;

    public Map<String, Object> extraProperties;

    public ID userId;

    public String userName;

    public ID tenantId;

    public String tenantName;

    public String clientId;

    public String correlationId;

    public String clientIpAddress;

    public String browserInfo;

    public Date creationTime;

    public SecurityLogInfo() {
        extraProperties = new HashMap<>();
    }

    @Override
    public String toString() {
        return String.format("SECURITY LOG: [{%s} - {%s} - {%s}]", applicationName, identity, applicationName);
    }
}
