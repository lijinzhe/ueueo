package com.ueueo.ddd.domain.entities.auditing;

import com.ueueo.ID;
import com.ueueo.auditing.IAuditedObjectWithUser;
import com.ueueo.ddd.domain.entities.IEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * This class can be used to simplify implementing <see cref="IAuditedObjectObject{TUser}"/> for aggregate roots.
 *
 * @author Lee
 * @date 2022-05-19 13:54
 */
@Getter
@Setter
public abstract class AuditedAggregateRootWithUser<TUser extends IEntity> extends AuditedAggregateRoot implements IAuditedObjectWithUser<TUser> {

    protected TUser creator;

    protected TUser lastModifier;

    public AuditedAggregateRootWithUser() {
        super();
    }

    public AuditedAggregateRootWithUser(ID id) {
        super(id);
    }

}
