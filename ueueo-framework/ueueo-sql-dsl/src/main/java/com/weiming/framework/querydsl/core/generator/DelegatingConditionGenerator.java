package com.weiming.framework.querydsl.core.generator;

import com.weiming.framework.querydsl.core.model.QueryCondition;

import java.util.List;

public class DelegatingConditionGenerator implements ConditionGenerator {

    private final List<ConditionGenerator> generators;

    public DelegatingConditionGenerator(List<ConditionGenerator> generators) {
        this.generators = generators;
    }

    @Override
    public boolean supports(String op) {
        return generators.stream().anyMatch(generator -> generator.supports(op));
    }

    @Override
    public String generate(QueryCondition condition) {
        return generators.stream().filter(generator -> generator.supports(condition.getOp())).findFirst()
                .map(generator -> generator.generate(condition)).orElseGet(() ->"");
    }

////    @Override
//    public String generate(String field, String op, Object value) {
//        return generators.stream().filter(generator -> generator.supports(op)).findFirst()
//                .map(generator -> generator.generate(field, op, value)).orElseGet(() ->"");
//    }
}
