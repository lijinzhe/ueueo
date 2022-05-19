package com.ueueo.ddd.domain.entities.auditing;

import com.ueueo.ID;
import com.ueueo.auditing.IAuditedObject;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * This class can be used to simplify implementing <see cref="IAuditedObject"/>.
 *
 * @author Lee
 * @date 2022-05-19 13:56
 */
@Getter
@Setter
public abstract class AuditedEntity extends CreationAuditedEntity implements IAuditedObject {
    protected Date lastModificationTime;
    protected ID lastModifierId;

    public AuditedEntity() {
        super();
    }

    public AuditedEntity(ID id) {
        super(id);
    }
}
