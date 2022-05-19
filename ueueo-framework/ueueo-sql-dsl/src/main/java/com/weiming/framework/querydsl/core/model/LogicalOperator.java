package com.weiming.framework.querydsl.core.model;

import java.util.Arrays;

public enum LogicalOperator {
    AND("AND"),
    OR("OR");

    private String op;
    LogicalOperator(String op) {
        this.op = op;
    }

    public String getOp() {
        return this.op;
    }

    public static LogicalOperator parse(String opStr ) {
        return Arrays.stream(values()).filter(logicalOperator -> logicalOperator.op.equalsIgnoreCase(opStr)).findFirst().
                orElseGet(()-> LogicalOperator.AND);
    }
}
