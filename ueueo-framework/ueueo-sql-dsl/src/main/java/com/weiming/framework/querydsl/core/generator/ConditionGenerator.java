package com.weiming.framework.querydsl.core.generator;


import com.weiming.framework.querydsl.core.model.QueryCondition;

public interface ConditionGenerator {
    boolean supports(String op);
    String generate(QueryCondition condition);
}
