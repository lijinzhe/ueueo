package com.weiming.framework.querydsl.sql.builder;

import com.weiming.framework.querydsl.core.generator.ConditionGenerator;
import com.weiming.framework.querydsl.core.generator.DefaultMultiConditionGenerator;
import com.weiming.framework.querydsl.core.generator.FunctionGenerator;
import com.weiming.framework.querydsl.core.generator.MultiConditionGenerator;
import com.weiming.framework.querydsl.core.model.ConditionItem;
import com.weiming.framework.querydsl.core.model.JoinOperator;
import com.weiming.framework.querydsl.core.model.OrderOperator;
import com.weiming.framework.querydsl.core.model.QueryCondition;
import com.weiming.framework.querydsl.sql.SQLDialect;
import com.weiming.framework.querydsl.sql.model.*;
import com.weiming.framework.querydsl.util.DslUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultSQLDialectBuilder implements SQLDialect {

    @Getter
    @Setter
    private FunctionGenerator functionGenerator;
    @Setter
    @Getter
    private ConditionGenerator conditionGenerator;

    private MultiConditionGenerator multiConditionGenerator;

    private String databaseType;

    public DefaultSQLDialectBuilder() {
        this.databaseType = "GENERIC";
        this.functionGenerator = SQLFunctionGeneratorFactory.createDefaultSQLFunctionGenerator();
        this.conditionGenerator = SQLConditionGeneratorFactory.createDefaultSQLConditionGenerator();
        this.multiConditionGenerator = new DefaultMultiConditionGenerator(SQLConditionGeneratorFactory.createDefaultSQLConditionGenerator());
    }

    @Override
    public String getDatabaseType() {
        return databaseType;
    }

    @Override
    public String quoteField(String field) {
        return DslUtils.quoteField(field);
    }

    @Override
    public boolean isSupportedFunction(String functionName) {
        return functionGenerator.supports(functionName);
    }

    @Override
    public boolean isAggregateFunction(String functionName) {
        //TODO:
        return functionGenerator.supports(functionName);
    }

    @Override
    public String build(SQLQuery sqlQuery) {
        StringBuilder sqlStr = new StringBuilder();
        sqlStr.append("SELECT ").append(buildSelectItems(sqlQuery.getSelect()));
        sqlStr.append(" FROM ").append(buildFrom(sqlQuery.getFrom()));
        if (sqlQuery.hasJoin()) {
            //JOIN
            sqlStr.append(" ").append(buildJoinItems(sqlQuery.getJoin()));
        }
        if (sqlQuery.hasWhere()) {
            sqlStr.append(" WHERE ").append(buildQueryConditions(sqlQuery.getWhere()));
        }

        if (sqlQuery.hasGroup()) {
            sqlStr.append(" GROUP BY ").append(buildGroupItems(sqlQuery.getGroup()));
            if (sqlQuery.hasHaving()) {
                sqlStr.append(" HAVING ").append(buildQueryConditions(sqlQuery.getHaving()));
            }
        }
        if (sqlQuery.hasOrder()) {
            sqlStr.append(" ORDER BY ").append(buildOrderItems(sqlQuery.getOrder()));
        }

        if (sqlQuery.hasLimit()) {
            if (sqlQuery.hasOffset()){
                sqlStr.append(" LIMIT ").append(sqlQuery.getOffset()).append(",").append(sqlQuery.getLimit());
            }else {
                sqlStr.append(" LIMIT ").append(sqlQuery.getLimit());
            }
        }
        return sqlStr.toString();
    }

    public String buildSelectItems(List<SelectItem> selectItems) {
        if (CollectionUtils.isEmpty(selectItems)) {
            return "";
        }
        return selectItems.stream().map(this::buildSelectItem).collect(Collectors.joining(","));
    }

    public String buildSelectItem(SelectItem selectItem) {
        String field = selectItem.getField();

        String selectItemContent = quoteField(selectItem.getField());

        // 有func的时候，DISTINCT不起作用
        if (selectItem.hasFunc()) {
            selectItemContent = buildFunctionExpression(selectItem.getFunc(), field);
        } else if (selectItem.isDistinct()) {
            selectItemContent = "DISTINCT " + selectItemContent;
        }
        if (selectItem.hasAs()) {
            selectItemContent = selectItemContent + " AS " + quoteField(selectItem.getAs());
        }
        return selectItemContent;
    }

    public String buildJoinItems(List<JoinItem> joinItems) {
        StringBuilder sqlStr = new StringBuilder();
        joinItems.stream().forEach(joinItem -> {
            sqlStr.append(buildJoinItem(joinItem)).append(" ");
        });
        return sqlStr.toString().trim();
    }
    public String buildJoinItem(JoinItem joinItem) {
        StringBuilder sqlStr = new StringBuilder();
        //Join type
        sqlStr.append(JoinOperator.parse(joinItem.getType()).getOp());
        sqlStr.append(" ").append(quoteField(joinItem.getFrom()));
        sqlStr.append(" ON ");
        ConditionItem onCondition = joinItem.getOn();
        if (onCondition.hasSubConditions()) {
            sqlStr.append(buildQueryConditions(onCondition.getConditions()));
        } else {
            sqlStr.append(buildQueryCondition(joinItem.getOn()));
        }
        return sqlStr.toString();
    }

    /**
     * 输出带括号的多条件组合
     *
     * @param queryConditions
     *
     * @return
     */
    public String buildQueryConditions(List<? extends QueryCondition> queryConditions) {
        return multiConditionGenerator.generate(queryConditions);
    }

    public String buildQueryCondition(QueryCondition queryCondition) {
        return multiConditionGenerator.generate(Arrays.asList(queryCondition));
    }

    public String buildFrom(String from) {
        return quoteField(from);
    }

    public String buildGroupItems(List<GroupItem> groupItems) {
        if (CollectionUtils.isEmpty(groupItems)) {
            return "";
        }
        return groupItems.stream().map(this::buildGroupItem).collect(Collectors.joining(","));
    }

    public String buildGroupItem(GroupItem groupItem) {
        //TODO: format rollup
        return quoteField(groupItem.getField());
    }

    public String buildOrderItems(List<OrderItem> orderItems) {
        if (CollectionUtils.isEmpty(orderItems)) {
            return "";
        }
        return orderItems.stream().map(this::buildOrderItem).collect(Collectors.joining(","));
    }

    public String buildOrderItem(OrderItem orderItem) {
        return quoteField(orderItem.getField()) + " " + OrderOperator.parse(orderItem.getSort()).getOp();
    }


    public String buildFunctionExpression(String func, String field) {
        //临时解决方案，先放这里，为了实现count(*)和count(1),稍后增加一个针对SelectItem的Generator
        if (func.equalsIgnoreCase("count") && (field.equals("*") || StringUtils.isNumeric(field))) {
            return functionGenerator.generate(func, field);
        }
        return functionGenerator.generate(func,  DslUtils.quoteField(field));
    }


}
