package com.ueueo.datamodels.management;

import com.ueueo.ddd.domain.entities.Entity;
import com.ueueo.ddd.domain.entities.IAggregateRoot;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-07-14 17:52
 */
public class DataModel extends Entity implements IAggregateRoot {

    private String name;
    private String providerName;
    private String providerKey;
}
