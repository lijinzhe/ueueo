package com.ueueo.auditing;

import com.ueueo.data.objectextending.ExtraPropertyDictionary;
import com.ueueo.data.objectextending.IHasExtraProperties;
import lombok.Data;

import java.util.Date;

/**
 * @author Lee
 * @date 2022-05-26 11:04
 */
@Data
public class AuditLogActionInfo implements IHasExtraProperties {
    private String serviceName;

    private String methodName;

    private String parameters;

    private Date executionTime;

    private long executionDuration;

    private ExtraPropertyDictionary extraProperties;

    public AuditLogActionInfo() {
        this.extraProperties = new ExtraPropertyDictionary();
    }
}
