package com.ueueo.ddd.domain.entities.auditing;

import com.ueueo.ID;
import com.ueueo.auditing.ICreationAuditedObject;
import com.ueueo.ddd.domain.entities.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * This class can be used to simplify implementing <see cref="T:Volo.Abp.Auditing.ICreationAuditedObject" /> for an entity.
 *
 * @author Lee
 * @date 2022-05-19 13:57
 */
@Getter
@Setter
public abstract class CreationAuditedEntity extends Entity implements ICreationAuditedObject {

    protected Date creationTime;

    protected ID creatorId;

    public CreationAuditedEntity() {
        super();
    }

    public CreationAuditedEntity(ID id) {
        super(id);
    }
}
