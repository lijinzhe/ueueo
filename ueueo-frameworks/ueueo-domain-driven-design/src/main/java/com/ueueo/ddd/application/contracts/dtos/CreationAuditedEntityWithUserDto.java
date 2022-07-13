package com.ueueo.ddd.application.contracts.dtos;

import com.ueueo.auditing.ICreationAuditedObjectWithUser;

/**
 * This class can be inherited by DTO classes to implement <see cref="ICreationAuditedObject{TCreator}"/> interface.
 * It also has the <see cref="Creator"/> object as a DTO represents the user.
 *
 * <typeparam name="TUserDto">Type of the User DTO</typeparam>
 */

public abstract class CreationAuditedEntityWithUserDto<TUserDto> extends CreationAuditedEntityDto implements ICreationAuditedObjectWithUser<TUserDto> {
    private TUserDto creator;

    @Override
    public TUserDto getCreator() {
        return creator;
    }

    @Override
    public void setCreator(TUserDto creator) {
        this.creator = creator;
    }
}
