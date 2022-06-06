package com.ueueo.ddd.application.contracts.dtos;

import com.ueueo.ID;
import com.ueueo.auditing.IAuditedObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * This class can be inherited by DTO classes to implement <see cref="IAuditedObject"/> interface.
 * It also implements the <see cref="IHasExtraProperties"/> interface.
 *
 * <typeparam name="TPrimaryKey">Type of primary key</typeparam>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class ExtensibleAuditedEntityDto extends ExtensibleCreationAuditedEntityDto implements IAuditedObject {

    private Date lastModificationTime;

    private ID lastModifierId;

    protected ExtensibleAuditedEntityDto() {
        this(true);
    }

    protected ExtensibleAuditedEntityDto(boolean setDefaultsForExtraProperties) {
        super(setDefaultsForExtraProperties);
    }
}

