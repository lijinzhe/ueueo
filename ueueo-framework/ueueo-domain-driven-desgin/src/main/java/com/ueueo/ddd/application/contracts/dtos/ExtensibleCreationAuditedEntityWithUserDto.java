package com.ueueo.ddd.application.contracts.dtos;

import com.ueueo.auditing.ICreationAuditedObjectWithUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This class can be inherited by DTO classes to implement <see cref="ICreationAuditedObject{TCreator}"/> interface.
 * It has the <see cref="Creator"/> object as a DTO represents the user.
 * It also implements the <see cref="IHasExtraProperties"/> interface.
 *
 * <typeparam name="TUserDto">Type of the User DTO</typeparam>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class ExtensibleCreationAuditedEntityWithUserDto<TUserDto> extends ExtensibleCreationAuditedEntityDto
        implements ICreationAuditedObjectWithUser<TUserDto> {
    private TUserDto creator;

    protected ExtensibleCreationAuditedEntityWithUserDto() {
        this(true);

    }

    protected ExtensibleCreationAuditedEntityWithUserDto(boolean setDefaultsForExtraProperties) {
        super(setDefaultsForExtraProperties);

    }
}
