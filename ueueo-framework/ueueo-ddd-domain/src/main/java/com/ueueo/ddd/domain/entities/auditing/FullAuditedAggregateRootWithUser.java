package com.ueueo.ddd.domain.entities.auditing;

import com.ueueo.ID;
import com.ueueo.auditing.IFullAuditedObjectWithUser;
import com.ueueo.ddd.domain.entities.IEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * Implements <see cref="IFullAuditedObject{TUser}"/> to be a base class for full-audited aggregate roots.
 *
 * @author Lee
 * @date 2022-05-19 14:07
 */
@Getter
@Setter
public abstract class FullAuditedAggregateRootWithUser<TUser extends IEntity> extends FullAuditedAggregateRoot implements IFullAuditedObjectWithUser<TUser> {
    protected TUser deleter;

    protected TUser creator;

    protected TUser lastModifier;

    public FullAuditedAggregateRootWithUser() {
        super();
    }

    public FullAuditedAggregateRootWithUser(ID id) {
        super(id);
    }
}
