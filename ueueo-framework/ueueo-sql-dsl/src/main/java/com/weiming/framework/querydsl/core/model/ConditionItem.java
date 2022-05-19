package com.weiming.framework.querydsl.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ConditionItem implements QueryCondition<ConditionItem> {
    private String field;
    /**
     * when it is null, return default value: =
     */
    private String op;
    //TODO: use object type for value?
    private Object value;
    /**
     * when it is null, return default value: AND
     * 规则：两个条件之间的关系，默认取后前面那个（第一个）条件的，忽略后观条件上的.
     */
    private String logicalOp;

    public ConditionItem(String field, Object value, String op, String logicalOp) {
        this.field = field;
        this.value = value;
        this.op = op;
        this.logicalOp = logicalOp;
    }

    private List<ConditionItem> conditions;

    @Override
    public String getField() {
        return field;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public String getLogicalOp() {
        return Optional.ofNullable(logicalOp).orElse(LogicalOperator.AND.getOp());
    }

    public String getOp() {
        return Optional.ofNullable(op).orElse(ConditionOperator.EQUAL.getOp());
    }

    @Override
    public boolean hasSubConditions() {
        return !CollectionUtils.isEmpty(conditions);
    }

    public List<ConditionItem> getConditions() {
        if (hasSubConditions()) {
            return Collections.unmodifiableList(conditions);
        }
        return Collections.EMPTY_LIST;
    }

    public static ConditionItem with(String field, Object value) {
        return new ConditionItem(field, value, null, null);
    }

    public static ConditionItem with(String field, Object value, String op) {
        return new ConditionItem(field, value, op, null);
    }

    public static ConditionItem with(String field, Object value, String op, String logicalOp) {
        return new ConditionItem(field, value, op, logicalOp);
    }

}
