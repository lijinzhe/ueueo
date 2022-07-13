package com.ueueo.ddd.application.contracts.dtos;

import com.ueueo.auditing.IAuditedObjectWithUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This class can be inherited by DTO classes to implement <see cref="IAuditedObject"/> interface.
 * It has the <see cref="Creator"/> and <see cref="LastModifier"/> objects as a DTOs represent the related user.
 * It also implements the <see cref="IHasExtraProperties"/> interface.
 *
 * <typeparam name="TUserDto">Type of the User DTO</typeparam>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class ExtensibleAuditedEntityWithUserDto<TUserDto> extends ExtensibleAuditedEntityDto implements IAuditedObjectWithUser<TUserDto> {
    private TUserDto creator;

    private TUserDto lastModifier;

    protected ExtensibleAuditedEntityWithUserDto() {
        this(true);

    }

    protected ExtensibleAuditedEntityWithUserDto(boolean setDefaultsForExtraProperties) {
        super(setDefaultsForExtraProperties);
    }
}
