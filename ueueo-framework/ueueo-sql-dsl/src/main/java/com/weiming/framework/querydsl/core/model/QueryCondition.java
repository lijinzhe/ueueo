package com.weiming.framework.querydsl.core.model;

import java.io.Serializable;
import java.util.List;

public interface QueryCondition<T extends QueryCondition> extends FieldItem, Serializable {
    String getOp();
    Object getValue();
    boolean hasSubConditions();
    String getLogicalOp();
    List<T> getConditions();
}
