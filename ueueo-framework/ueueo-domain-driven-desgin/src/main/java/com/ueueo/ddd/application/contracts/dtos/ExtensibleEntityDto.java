package com.ueueo.ddd.application.contracts.dtos;

import com.ueueo.ID;
import com.ueueo.data.objectextending.ExtensibleObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class ExtensibleEntityDto extends ExtensibleObject implements IEntityDto {
    /**
     * Id of the entity.
     */
    private ID id;

    protected ExtensibleEntityDto() {
        this(true);

    }

    protected ExtensibleEntityDto(boolean setDefaultsForExtraProperties) {
        super(setDefaultsForExtraProperties);
    }

    @Override
    public String toString() {
        return String.format("[DTO: %s] Id = %s", getClass().getName(), id.toString());
    }
}
