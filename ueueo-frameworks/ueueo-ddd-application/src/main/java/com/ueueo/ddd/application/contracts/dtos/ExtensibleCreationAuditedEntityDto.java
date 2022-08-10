package com.ueueo.ddd.application.contracts.dtos;

import com.ueueo.ID;
import com.ueueo.auditing.ICreationAuditedObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * This class can be inherited by DTO classes to implement <see cref="ICreationAuditedObject"/> interface.
 * It also implements the <see cref="IHasExtraProperties"/> interface.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class ExtensibleCreationAuditedEntityDto extends ExtensibleEntityDto implements ICreationAuditedObject {

    private Date creationTime;

    private ID creatorId;

    protected ExtensibleCreationAuditedEntityDto() {
        this(true);
    }

    protected ExtensibleCreationAuditedEntityDto(boolean setDefaultsForExtraProperties) {
        super(setDefaultsForExtraProperties);
    }
}
