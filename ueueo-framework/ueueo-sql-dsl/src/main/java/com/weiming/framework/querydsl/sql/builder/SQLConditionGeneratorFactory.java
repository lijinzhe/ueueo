package com.weiming.framework.querydsl.sql.builder;

import com.weiming.framework.querydsl.core.generator.ConditionGenerator;
import com.weiming.framework.querydsl.core.generator.DefaultConditionGenerator;
import com.weiming.framework.querydsl.core.generator.DelegatingConditionGenerator;
import com.weiming.framework.querydsl.util.DslUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.weiming.framework.querydsl.core.model.ConditionOperator.*;

public class SQLConditionGeneratorFactory {

    /**
     * 针对不同的数据库实现语句，可以在这里创建多个DelegatingSQLConditionGenerator
     *
     * @return
     */
    public static ConditionGenerator createDefaultSQLConditionGenerator() {
        return new DelegatingConditionGenerator(Arrays.asList(
                new DefaultConditionGenerator(EQUAL),
                new DefaultConditionGenerator(GREATER_THAN),
                new DefaultConditionGenerator(LESS_THAN),
                new DefaultConditionGenerator(NOT_EQUAL),
                new DefaultConditionGenerator(GREATER_OR_EQUAL),
                new DefaultConditionGenerator(LESS_OR_EQUAL),
                new DefaultConditionGenerator(CONTAINS, () -> "LIKE", value -> DslUtils.quoteValue("%" + value + "%")),
                new DefaultConditionGenerator(STARTS_WITH, () -> "LIKE", value -> DslUtils.quoteValue(value + "%")),
                new DefaultConditionGenerator(ENDS_WITH, () -> "LIKE", value -> DslUtils.quoteValue("%" + value)),
                new DefaultConditionGenerator(IS_NULL, () -> "IS NULL"),
                new DefaultConditionGenerator(IS_NOT_NULL, () -> "IS NOT NULL"),
                new DefaultConditionGenerator(IN, () -> "IN", DefaultConditionGenerator.MultiValueExpression(Collectors.joining(",", "(", ")"))),
                new DefaultConditionGenerator(BETWEEN, () -> "BETWEEN", DefaultConditionGenerator.MultiValueExpression(Collectors.joining(" AND ")))
        ));
    }
}
