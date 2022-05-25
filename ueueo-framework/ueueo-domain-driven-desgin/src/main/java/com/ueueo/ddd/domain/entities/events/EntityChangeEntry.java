package com.ueueo.ddd.domain.entities.events;

import com.ueueo.auditing.EntityChangeType;
import lombok.Data;

/**
 * @author Lee
 * @date 2022-05-25 14:46
 */
@Data
public class EntityChangeEntry {
    private Object entity;
    private EntityChangeType changeType;

    public EntityChangeEntry(Object entity, EntityChangeType changeType) {
        this.entity = entity;
        this.changeType = changeType;
    }
}
