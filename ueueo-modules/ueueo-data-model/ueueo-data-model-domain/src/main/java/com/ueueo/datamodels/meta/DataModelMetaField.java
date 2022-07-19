package com.ueueo.datamodels.meta;

import com.ueueo.util.Check;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * @author Lee
 * @date 2022-07-14 17:38
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DataModelMetaField {

    /**
     * Default <see cref="Order"/> value of a menu item.
     */
    public final int DefaultOrder = 1000;

    /**
     * Unique name of the field in the application.
     */
    @NonNull
    @EqualsAndHashCode.Include
    public String name;
    /**
     * Display name of the menu item.
     */
    @NonNull
    public String displayName;

    /**
     * The Display order of the menu.
     * Default value: 1000.
     */
    public int order;

    @Nullable
    public String type;

    public DataModelMetaField(
            @NonNull String name,
            @NonNull String displayName,
            Integer order) {
        Check.notNullOrWhiteSpace(name, "name");
        Check.notNullOrWhiteSpace(displayName, "displayName");

        this.name = name;
        this.displayName = displayName;
        this.order = order != null ? order : DefaultOrder;
    }

    private String getDefaultElementId() {
        return "MenuItem_" + name;
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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Nullable
    public String getType() {
        return type;
    }

    public void setType(@Nullable String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "[DataModelMetaField] Name = " + name;
    }
}
