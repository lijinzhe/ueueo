package com.ueueo.ddd.domain.entities.auditing;

import com.ueueo.ID;
import com.ueueo.auditing.ICreationAuditedObject;
import com.ueueo.ddd.domain.entities.AggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * This class can be used to simplify implementing <see cref="ICreationAuditedObject"/> for aggregate roots.
 *
 * @author Lee
 * @date 2022-05-19 11:51
 */
@Getter
@Setter
public abstract class CreationAuditedAggregateRoot extends AggregateRoot<ID> implements ICreationAuditedObject {

    protected Date creationTime;

    protected ID creatorId;

    public CreationAuditedAggregateRoot() {
        super();
    }

    public CreationAuditedAggregateRoot(ID id) {
        super(id);
    }
}
