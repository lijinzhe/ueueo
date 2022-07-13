package com.ueueo.ddd.application.contracts.dtos;

import com.ueueo.ID;
import com.ueueo.auditing.IAuditedObject;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * This class can be inherited by DTO classes to implement <see cref="IAuditedObject"/> interface.
 */
@EqualsAndHashCode(callSuper = true)
public abstract class AuditedEntityDto extends CreationAuditedEntityDto implements IAuditedObject {

    private Date lastModificationTime;

    private ID lastModifierId;

    @Override
    public Date getLastModificationTime() {
        return lastModificationTime;
    }

    @Override
    public ID getLastModifierId() {
        return lastModifierId;
    }

    @Override
    public void setLastModificationTime(Date lastModificationTime) {
        this.lastModificationTime = lastModificationTime;
    }

    @Override
    public void setLastModifierId(ID lastModifierId) {
        this.lastModifierId = lastModifierId;
    }
}
