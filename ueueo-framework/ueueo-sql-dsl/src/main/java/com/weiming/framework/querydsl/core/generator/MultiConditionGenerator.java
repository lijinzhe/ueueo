package com.weiming.framework.querydsl.core.generator;


import com.weiming.framework.querydsl.core.model.QueryCondition;

import java.util.List;

public interface MultiConditionGenerator {
    String generate(List<? extends QueryCondition> conditions);
}
