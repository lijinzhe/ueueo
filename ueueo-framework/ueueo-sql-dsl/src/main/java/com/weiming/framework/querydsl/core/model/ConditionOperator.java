/*
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright (c) 2009 - 2017 Hitachi Vantara.  All rights reserved.
 */
package com.weiming.framework.querydsl.core.model;

import java.util.Arrays;

/**
 * Operator is used in the definition of a @see MqlCondition
 */
public enum ConditionOperator {
  EQUAL( "=", true, 2),
  NOT_EQUAL( "<>", true, 2),
  GREATER_THAN( ">", true, 1),
  LESS_THAN( "<", true, 1),
  GREATER_OR_EQUAL( ">=", true, 1),
  LESS_OR_EQUAL( "<=", true, 1),

  CONTAINS( "Contains", true, 0),
  STARTS_WITH( "StartsWith", true, 0),
  ENDS_WITH( "EndsWith", true, 0),

  BETWEEN( "Between", true, 2),
  IS_NULL( "IsNull", false, 2),
  IS_NOT_NULL( "IsNotNull", false, 2),
  IN ( "IN", true, 2);

  private String op;
  /**
   *  可以用来做校验
   *  0 = string, 1 = numeric,2 = both
    */
  private int dataType;
  private boolean valueRequired;

  ConditionOperator(String op, boolean valueRequired, int dataType) {
    this.op = op;
    this.valueRequired = valueRequired;
    this.dataType = dataType;
  }

  public String getOp() {
    return this.op;
  }

  public boolean isValueRequired() {
    return valueRequired;
  }

  public int getData() {
    return dataType;
  }

  public String toString() {
    return op;
  }

  public static ConditionOperator parse(String opStr ) {
    return Arrays.stream(values()).filter(conditionOperator -> conditionOperator.op.equalsIgnoreCase(opStr)).findFirst().
            orElseGet(()-> ConditionOperator.EQUAL);
  }



}
