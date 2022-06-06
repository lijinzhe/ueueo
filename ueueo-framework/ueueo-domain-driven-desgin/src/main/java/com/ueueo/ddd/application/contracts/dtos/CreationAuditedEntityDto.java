package com.ueueo.ddd.application.contracts.dtos;

import com.ueueo.ID;
import com.ueueo.auditing.ICreationAuditedObject;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * This class can be inherited by DTO classes to implement <see cref="ICreationAuditedObject"/> interface.
 */
@EqualsAndHashCode(callSuper = true)
public abstract class CreationAuditedEntityDto extends EntityDto implements ICreationAuditedObject {

    private Date creationTime;

    private ID creatorId;

    @Override
    public Date getCreationTime() {
        return creationTime;
    }

    @Override
    public ID getCreatorId() {
        return creatorId;
    }

    @Override
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public void setCreatorId(ID creatorId) {
        this.creatorId = creatorId;
    }
}

