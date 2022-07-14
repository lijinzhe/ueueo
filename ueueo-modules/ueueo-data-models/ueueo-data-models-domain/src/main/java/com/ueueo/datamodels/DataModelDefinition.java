package com.ueueo.datamodels;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Lee
 * @date 2022-07-14 11:38
 */
@Data
public class DataModelDefinition {

    /** Unique name of the data model. */
    @Setter(AccessLevel.PRIVATE)
    private String name;

    private String displayName;

    private String description;

    /**
     * A list of allowed providers to get/set value of this data model.
     * An empty list indicates that all providers are allowed.
     */
    @Setter(AccessLevel.PRIVATE)
    private List<String> providers;

    public DataModelDefinition(String name, String displayName, String description) {
        Objects.requireNonNull(name);
        this.name = name;
        this.displayName = displayName;
        this.description = description;

        this.providers = new ArrayList<>();
    }

}
