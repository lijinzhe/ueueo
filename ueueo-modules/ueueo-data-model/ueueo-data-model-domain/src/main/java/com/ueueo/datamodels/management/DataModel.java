package com.ueueo.datamodels.management;

import com.ueueo.ID;
import com.ueueo.datamodels.meta.DataModelMeta;
import com.ueueo.ddd.domain.entities.Entity;
import com.ueueo.ddd.domain.entities.IAggregateRoot;
import lombok.Data;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-07-14 17:52
 */
@Data
public class DataModel extends Entity<ID> implements IAggregateRoot<ID> {

    public DataModel(ID id, String name, DataModelMeta meta, String providerName, String providerKey) {
        super(id);
        this.name = name;
        this.meta = meta;
        this.providerName = providerName;
        this.providerKey = providerKey;
    }

    private String name;
    private DataModelMeta meta;
    private String providerName;
    private String providerKey;
}
