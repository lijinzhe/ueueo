package com.ueueo.ddd.domain.entities.auditing;

import com.ueueo.ID;
import com.ueueo.auditing.IFullAuditedObject;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Implements <see cref="IFullAuditedObject"/> to be a base class for full-audited aggregate roots.
 *
 * @author Lee
 * @date 2022-05-18 15:16
 */
@Getter
@Setter
public abstract class FullAuditedAggregateRoot extends AuditedAggregateRoot implements IFullAuditedObject {
    protected boolean isDeleted;

    protected ID deleterId;

    protected Date deletionTime;

    public FullAuditedAggregateRoot() {
        super();
    }

    public FullAuditedAggregateRoot(ID id) {
        super(id);
    }

}
