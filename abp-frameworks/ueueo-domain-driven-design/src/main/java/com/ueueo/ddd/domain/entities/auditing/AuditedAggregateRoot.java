package com.ueueo.ddd.domain.entities.auditing;

import com.ueueo.ID;
import com.ueueo.auditing.IAuditedObject;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * This class can be used to simplify implementing <see cref="IAuditedObject"/> for aggregate roots.
 *
 * @author Lee
 * @date 2022-05-18 15:16
 */
@Getter
@Setter
public abstract class AuditedAggregateRoot extends CreationAuditedAggregateRoot implements IAuditedObject {

    protected Date lastModificationTime;
    protected ID lastModifierId;

    public AuditedAggregateRoot() {
        super();
    }

    public AuditedAggregateRoot(ID id) {
        super(id);
    }

}
