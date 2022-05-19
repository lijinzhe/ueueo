package com.weiming.framework.querydsl.core.generator;

import com.weiming.framework.querydsl.core.NativeOperatorSupplier;
import com.weiming.framework.querydsl.core.ValueExpression;
import com.weiming.framework.querydsl.core.model.ConditionOperator;
import com.weiming.framework.querydsl.core.model.QueryCondition;
import com.weiming.framework.querydsl.util.DslUtils;

import java.util.List;
import java.util.stream.Collector;

public class DefaultConditionGenerator implements ConditionGenerator {

    final private ConditionOperator conditionOperator;
    /**
     * 将条件操作符转换为具体某数据库native的实现操作符
     */
    final private NativeOperatorSupplier nativeOperatorSupplier;
    /**
     * 将被查询的值转换为具体某数据库native的表达式
     */
    final private ValueExpression valueExpression;

//    final private Collector<? super String, ?, String> multiValueCollector;


    public DefaultConditionGenerator(ConditionOperator conditionOperator) {
        this(conditionOperator, null, null);
    }

    public DefaultConditionGenerator(ConditionOperator conditionOperator, NativeOperatorSupplier nativeOperatorSupplier) {
        this(conditionOperator, nativeOperatorSupplier, null);
    }

    public DefaultConditionGenerator(ConditionOperator conditionOperator, NativeOperatorSupplier nativeOperatorSupplier
            , ValueExpression valueExpression) {
        this.conditionOperator = conditionOperator;
        this.nativeOperatorSupplier = nativeOperatorSupplier != null ? nativeOperatorSupplier : () -> conditionOperator.getOp();
//        this.multiValueCollector = multiValueCollector != null ? multiValueCollector : Collectors.joining(",", "(", ")");
        this.valueExpression = valueExpression != null ? valueExpression : SingleValueExpression();

    }

    public static ValueExpression MultiValueExpression(Collector<? super String, ?, String> collector) {
        return value -> {
            if (value instanceof List) {
                List<Object> valueList = (List<Object>) value;
                return (String) valueList.stream().map(SingleValueExpression()
                ).collect(collector);
            } else {
                return SingleValueExpression().apply(value);
            }
        };
    }

    public static ValueExpression SingleValueExpression() {
        return value -> {
            if (value instanceof String) {
                return DslUtils.quoteValue((String) value);
            } else if (value instanceof Number) {
                return String.valueOf(value);
            } else {
                return value.toString();
            }
        };
    }







    @Override
    public boolean supports(String op) {
        return conditionOperator == ConditionOperator.parse(op);
    }

    @Override
    public String generate(QueryCondition condition) {
        return generate(condition.getField(), condition.getOp(), condition.getValue());
    }

    /**
     * @param field
     * @param op    不直接使用这个参数，只是在外面用来做判断的，需要使用内部conditionOperator或nativeOp
     * @param value
     *
     * @return
     */
    private String generate(String field, String op, Object value) {
        StringBuffer content = new StringBuffer();
        content.append(quoteField(field)).append(" ");
        content.append(nativeOperatorSupplier.get());
        if (conditionOperator.isValueRequired()) {
            content.append(" ");
            content.append(valueExpression.apply(value));
        }
        return content.toString();
    }

    public String quoteField(String field) {
        return DslUtils.quoteField(field);
    }
}
