package com.weiming.framework.querydsl.core.model;

import java.util.Arrays;

public enum OrderOperator {
    DESC("DESC"),
    ASC("ASC");

    private String op;
    OrderOperator(String op) {
        this.op = op;
    }

    public String getOp() {
        return this.op;
    }

    public static OrderOperator parse(String opStr ) {
        return Arrays.stream(values()).filter(orderOperator -> orderOperator.op.equalsIgnoreCase(opStr)).findFirst().
                orElseGet(()-> OrderOperator.DESC);
    }
}
