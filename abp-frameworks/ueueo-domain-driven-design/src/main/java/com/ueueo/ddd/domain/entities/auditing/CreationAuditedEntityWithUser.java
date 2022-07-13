package com.ueueo.ddd.domain.entities.auditing;

import com.ueueo.ID;
import com.ueueo.auditing.ICreationAuditedObjectWithUser;
import lombok.Getter;
import lombok.Setter;

/**
 * This class can be used to simplify implementing <see cref="ICreationAuditedObject{TCreator}"/>.
 *
 * @author Lee
 * @date 2022-05-19 14:08
 */
@Getter
@Setter
public abstract class CreationAuditedEntityWithUser<TUser> extends CreationAuditedEntity implements ICreationAuditedObjectWithUser<TUser> {

    protected TUser creator;

    public CreationAuditedEntityWithUser() {
        super();
    }

    public CreationAuditedEntityWithUser(ID id) {
        super(id);
    }
}
