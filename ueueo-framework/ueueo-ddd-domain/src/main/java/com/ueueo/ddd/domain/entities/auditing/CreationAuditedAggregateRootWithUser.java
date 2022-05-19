package com.ueueo.ddd.domain.entities.auditing;

import com.ueueo.ID;
import com.ueueo.auditing.ICreationAuditedObjectWithUser;
import lombok.Getter;
import lombok.Setter;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-19 14:09
 */
@Getter
@Setter
public abstract class CreationAuditedAggregateRootWithUser<TUser> extends CreationAuditedAggregateRoot implements ICreationAuditedObjectWithUser<TUser> {
    protected TUser creator;

    public CreationAuditedAggregateRootWithUser() {
        super();
    }

    public CreationAuditedAggregateRootWithUser(ID id) {
        super(id);
    }
}
