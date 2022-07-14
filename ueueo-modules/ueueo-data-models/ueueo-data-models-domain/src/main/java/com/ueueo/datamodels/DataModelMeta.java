package com.ueueo.datamodels;

import com.ueueo.exception.BaseException;
import com.ueueo.util.Check;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * @author Lee
 * @date 2022-07-14 17:38
 */
public class DataModelMeta {

    /** Unique name of the data model in the application. */
    @NonNull
    public String name;

    /**
     * Display name of the Data Model.
     */
    @NonNull
    private String displayName;

    @NonNull
    public DataModelMetaFieldList fields;

    /**
     * Can be used to store a custom object related to this data model.
     */
    @Nullable
    public Map<String, Object> customData;

    public DataModelMeta(@NonNull String name, String displayName) {
        Check.notNullOrWhiteSpace(name, "name");

        this.name = name;
        this.displayName = displayName != null ? displayName : this.name;

        this.fields = new DataModelMetaFieldList();
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        Assert.isTrue(StringUtils.isNotBlank(displayName), "DisplayName must not blank!");
        this.displayName = displayName;
    }

    @NonNull
    public DataModelMetaFieldList getFields() {
        return fields;
    }

    public DataModelMeta addField(@NonNull DataModelMetaField field) {
        fields.add(field);
        return this;
    }

    @Nullable
    public Map<String, Object> getCustomData() {
        return customData;
    }

    public void setCustomData(@Nullable Map<String, Object> customData) {
        this.customData = customData;
    }

    public DataModelMetaField getField(String fieldName) {
        DataModelMetaField field = getFieldOrNull(fieldName);
        if (field == null) {
            throw new BaseException("Could not find a field with given name: " + fieldName);
        }

        return field;
    }

    public DataModelMetaField getFieldOrNull(String fieldName) {
        return getFields().stream().filter(mi -> mi.getName().equals(fieldName)).findFirst().orElse(null);
    }

    public boolean tryRemoveField(String fieldName) {
        return getFields().removeIf(item -> item.getName().equals(fieldName));
    }

    public DataModelMeta setFieldOrder(String fieldName, int order) {
        DataModelMetaField field = getFieldOrNull(fieldName);
        if (field != null) {
            field.setOrder(order);
        }

        return this;
    }

    @Override
    public String toString() {
        return "[DataModel] Name = " + name;
    }
}
