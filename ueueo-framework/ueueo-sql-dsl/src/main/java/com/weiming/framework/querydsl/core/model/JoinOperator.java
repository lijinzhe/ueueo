
package com.weiming.framework.querydsl.core.model;

import java.util.Arrays;

public enum JoinOperator {

  INNER("INNER JOIN"),
  LEFT("LEFT JOIN"),
  RIGHT("RIGHT JOIN");
  private String op;

  JoinOperator(String op) {
    this.op = op;
  }

  public String getOp() {
    return this.op;
  }

  public static JoinOperator parse(String opStr ) {
    return Arrays.stream(values()).filter(joinOperator -> joinOperator.name().equalsIgnoreCase(opStr)).findFirst().
            orElseGet(()-> JoinOperator.LEFT);
  }
}
