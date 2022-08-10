package com.ueueo.ddd.application.contracts.dtos;

import com.ueueo.ID;
import lombok.Data;

@Data
public abstract class EntityDto implements IEntityDto {
    /**
     * Id of the entity.
     */
    private ID id;

    @Override
    public String toString() {
        return String.format("[DTO: %s] Id = %s", getClass().getName(), id);
    }
}
