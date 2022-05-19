package com.weiming.framework.querydsl.sql.model;

import com.weiming.framework.querydsl.core.model.QueryCondition;

import java.io.Serializable;
import java.util.List;

public interface SQLQuery extends Serializable, Cloneable {


    List<SelectItem> getSelect();

    String getFrom();

    List<QueryCondition> getWhere();
    boolean  hasWhere();
    List<OrderItem> getOrder();
    boolean  hasOrder();
    List<GroupItem> getGroup();
    boolean  hasGroup();
    List<QueryCondition> getHaving();
    boolean  hasHaving();
    List<JoinItem> getJoin();
    boolean  hasJoin();
    Integer getLimit();
    boolean  hasLimit();
    Integer getOffset();
    boolean  hasOffset();

    SQLQuery clone();

    static SQLQueryBuilder builder() {
        return new SQLQueryBuilder();
    }

    static SQLQuery countSQLQuery(SQLQuery sqlQuery) {
        return SQLQueryBuilder.buildCountSQLQuery(sqlQuery);
    }

}
