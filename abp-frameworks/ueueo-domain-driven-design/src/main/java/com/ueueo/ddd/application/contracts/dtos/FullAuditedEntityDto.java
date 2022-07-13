package com.ueueo.ddd.application.contracts.dtos;

import com.ueueo.ID;
import com.ueueo.auditing.IFullAuditedObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * This class can be inherited by DTO classes to implement <see cref="IFullAuditedObject"/> interface.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class FullAuditedEntityDto extends AuditedEntityDto implements IFullAuditedObject {

    private boolean isDeleted;

    private ID deleterId;

    private Date deletionTime;
}

