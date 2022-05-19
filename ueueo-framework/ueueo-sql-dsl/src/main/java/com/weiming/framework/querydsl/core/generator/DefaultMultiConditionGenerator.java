package com.weiming.framework.querydsl.core.generator;

import com.weiming.framework.querydsl.core.model.LogicalOperator;
import com.weiming.framework.querydsl.core.model.QueryCondition;
import org.apache.commons.collections.CollectionUtils;

import java.util.Iterator;
import java.util.List;

public class DefaultMultiConditionGenerator implements MultiConditionGenerator {

    private ConditionGenerator conditionGenerator;

    public DefaultMultiConditionGenerator(ConditionGenerator conditionGenerator) {
        this.conditionGenerator = conditionGenerator;
    }

    @Override
    public String generate(List<? extends QueryCondition> conditions) {
        if (CollectionUtils.isEmpty(conditions)) {
            return "";
        } else if (conditions.size() == 1) {
            return buildQueryCondition(conditions.get(0));
        } else {
            return buildQueryConditions(conditions);
        }
    }

    private String buildQueryConditions(List<? extends QueryCondition> queryConditions) {
        if (CollectionUtils.isEmpty(queryConditions)) {
            return "";
        }
        Iterator<? extends QueryCondition> iterator = queryConditions.iterator();
        QueryCondition firstItem = iterator.next();
        String logicalOp = firstItem.getLogicalOp();
        String startQueryContent = buildQueryCondition(firstItem);
        while (iterator.hasNext()) {
            QueryCondition next = iterator.next();
            startQueryContent = startQueryContent + " " + LogicalOperator.parse(logicalOp).getOp() + " " + buildQueryCondition(next);
            logicalOp = next.getLogicalOp();
        }
        return startQueryContent;
    }

    private String buildQueryCondition(QueryCondition queryCondition) {
        if (queryCondition.hasSubConditions()) {
            return "(" + buildQueryConditions(queryCondition.getConditions()) + ")";
        } else {
            return conditionGenerator.generate(queryCondition);
        }
    }


}
