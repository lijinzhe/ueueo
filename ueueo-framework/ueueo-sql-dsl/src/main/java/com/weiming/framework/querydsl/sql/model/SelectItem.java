package com.weiming.framework.querydsl.sql.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SelectItem implements Serializable {
    private String field;
    private String as;
    private String func;
    private boolean distinct = false;

    public SelectItem(String field) {
        this.field = field;
    }

    public boolean hasFunc() {
        return StringUtils.isNotBlank(getFunc());
    }

    public boolean hasAs() {
        return StringUtils.isNotBlank(getAs());
    }

    public static SelectItem defaultSelectCountItem() {
        return SelectItem.builder().field("1").func("count").build();
    }

}
