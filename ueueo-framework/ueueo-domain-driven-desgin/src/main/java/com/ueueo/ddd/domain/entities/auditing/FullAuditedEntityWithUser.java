package com.ueueo.ddd.domain.entities.auditing;

import com.ueueo.ID;
import com.ueueo.auditing.IFullAuditedObjectWithUser;
import com.ueueo.ddd.domain.entities.IEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * Implements <see cref="IFullAuditedObject{TUser}"/> to be a base class for full-audited entities.
 *
 * @author Lee
 * @date 2022-05-19 14:06
 */
@Getter
@Setter
public abstract class FullAuditedEntityWithUser<TUser extends IEntity> extends FullAuditedEntity implements IFullAuditedObjectWithUser<TUser> {
    protected TUser deleter;

    protected TUser creator;

    protected TUser lastModifier;

    public FullAuditedEntityWithUser() {
        super();
    }

    public FullAuditedEntityWithUser(ID id) {
        super(id);
    }
}
