package com.ueueo.ddd.application.contracts.dtos;

import com.ueueo.auditing.IAuditedObjectWithUser;

/**
 * This class can be inherited by DTO classes to implement <see cref="IAuditedObject"/> interface.
 * It has the <see cref="Creator"/> and <see cref="LastModifier"/> objects as a DTOs represent the related user.
 *
 * <typeparam name="TUserDto">Type of the User DTO</typeparam>
 */
public abstract class AuditedEntityWithUserDto<TUserDto> extends AuditedEntityDto implements IAuditedObjectWithUser<TUserDto> {

    private TUserDto creator;

    private TUserDto lastModifier;

    @Override
    public TUserDto getCreator() {
        return creator;
    }

    @Override
    public void setCreator(TUserDto creator) {
        this.creator = creator;
    }

    @Override
    public TUserDto getLastModifier() {
        return lastModifier;
    }

    @Override
    public void setLastModifier(TUserDto lastModifier) {
        this.lastModifier = lastModifier;
    }
}
