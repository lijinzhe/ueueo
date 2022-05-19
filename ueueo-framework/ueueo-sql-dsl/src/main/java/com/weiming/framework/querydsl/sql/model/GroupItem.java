package com.weiming.framework.querydsl.sql.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupItem implements Serializable {
    private String field;
    private String rollup;
    public GroupItem(String field) {
        this.field = field;
    }
}
