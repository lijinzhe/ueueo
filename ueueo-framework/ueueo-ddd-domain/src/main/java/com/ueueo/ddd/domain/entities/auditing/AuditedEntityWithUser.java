package com.ueueo.ddd.domain.entities.auditing;

import com.ueueo.ID;
import com.ueueo.auditing.IAuditedObjectWithUser;
import com.ueueo.ddd.domain.entities.IEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * This class can be used to simplify implementing <see cref="IAuditedObject{TUser}"/>.
 *
 * @author Lee
 * @date 2022-05-19 14:17
 */
@Getter
@Setter
public abstract class AuditedEntityWithUser<TUser extends IEntity> extends AuditedEntity implements IAuditedObjectWithUser<TUser> {
    protected TUser creator;

    protected TUser lastModifier;

    public AuditedEntityWithUser() {
        super();
    }

    public AuditedEntityWithUser(ID id) {
        super(id);
    }
}
