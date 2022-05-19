package com.ueueo.ddd.domain.entities.auditing;

import com.ueueo.ID;
import com.ueueo.auditing.IFullAuditedObject;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Implements <see cref="IFullAuditedObject"/> to be a base class for full-audited entities.
 *
 * @author Lee
 * @date 2022-05-19 14:06
 */
@Getter
@Setter
public abstract class FullAuditedEntity extends AuditedEntity implements IFullAuditedObject {
    protected boolean isDeleted;

    protected ID deleterId;

    protected Date deletionTime;

    public FullAuditedEntity() {
        super();
    }

    public FullAuditedEntity(ID id) {
        super(id);
    }
}
