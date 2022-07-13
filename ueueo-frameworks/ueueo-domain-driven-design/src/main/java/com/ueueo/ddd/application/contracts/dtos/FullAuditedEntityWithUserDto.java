package com.ueueo.ddd.application.contracts.dtos;

import com.ueueo.auditing.IFullAuditedObjectWithUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This class can be inherited by DTO classes to implement <see cref="IFullAuditedObject{TUser}"/> interface.
 * It has the <see cref="Creator"/>, <see cref="LastModifier"/> and <see cref="Deleter"/> objects as a DTOs represent the related user.
 *
 * <typeparam name="TUserDto">Type of the User</typeparam>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class FullAuditedEntityWithUserDto<TUserDto> extends FullAuditedEntityDto implements IFullAuditedObjectWithUser<TUserDto> {

    private TUserDto creator;

    private TUserDto lastModifier;

    private TUserDto deleter;
}
