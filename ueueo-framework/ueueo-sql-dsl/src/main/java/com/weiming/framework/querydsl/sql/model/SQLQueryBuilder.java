package com.weiming.framework.querydsl.sql.model;

import com.weiming.framework.querydsl.core.model.ConditionItem;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.Builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLQueryBuilder implements Builder<SQLQuery> {
    private List<SelectItem> select = new ArrayList<>();
    private String from;
    private List<ConditionItem> where = new ArrayList<>();
    private List<GroupItem> group = new ArrayList<>();
    private List<ConditionItem> having = new ArrayList<>();
    private List<OrderItem> order = new ArrayList<>();
    private List<JoinItem> join = new ArrayList<>();

    private Integer limit;
    private Integer offset;

    public SQLQueryBuilder copyOf(SQLQuery sqlQuery) {
        SQLQuery sqlQueryCopy = sqlQuery.clone();
        selects(sqlQueryCopy.getSelect());
        from(sqlQueryCopy.getFrom());
        joins(sqlQueryCopy.getJoin());
        // 重新实例化 ConditionItem，否则需要在SQLQuery中直接使用ConditionItem类型，而不是QueryCondition
        // 是否有这个必要？
        sqlQueryCopy.getWhere().forEach( queryCondition -> where(ConditionItem.with(queryCondition.getField(),
                queryCondition.getValue(), queryCondition.getOp(), queryCondition.getLogicalOp())));
        groups(sqlQueryCopy.getGroup());
        // 同上
        sqlQueryCopy.getHaving().forEach( queryCondition -> having(ConditionItem.with(queryCondition.getField(),
                queryCondition.getValue(), queryCondition.getOp(), queryCondition.getLogicalOp())));
        orders(sqlQueryCopy.getOrder());
        limit(sqlQueryCopy.getLimit());
        offset(sqlQueryCopy.getOffset());
        return this;
    }

    public SQLQueryBuilder clearSelects() {
        select.clear();
        return this;
    }

    public SQLQueryBuilder select(String column) {
        selects(Validate.notBlank(column));
        return this;
    }

    public SQLQueryBuilder selects(String... columns) {
        Arrays.stream(Validate.notNull(columns)).map(SelectItem::new).forEach(select::add);
        return this;
    }

    public SQLQueryBuilder selects(List<SelectItem> selectItems) {
        select.addAll(Validate.notNull(selectItems));
        return this;
    }

    public SQLQueryBuilder select(SelectItem selectItem) {
        select.add(Validate.notNull(selectItem));
        return this;
    }

    public SQLQueryBuilder from(String from) {
        this.from = Validate.notNull(from);
        return this;
    }

    public SQLQueryBuilder clearWheres() {
        where.clear();
        return this;
    }

    public SQLQueryBuilder where(ConditionItem whereItem) {
        where.add(Validate.notNull(whereItem));
        return this;
    }

    public SQLQueryBuilder wheres(List<ConditionItem> whereItems) {
        where.addAll(Validate.notNull(whereItems));
        return this;
    }


    public SQLQueryBuilder having(ConditionItem havingItem) {
        having.add(Validate.notNull(havingItem));
        return this;
    }

    public SQLQueryBuilder havings(List<ConditionItem> havingItems) {
        having.addAll(Validate.notNull(havingItems));
        return this;
    }
    public SQLQueryBuilder clearHavings() {
        having.clear();
        return this;
    }

    public SQLQueryBuilder group(String column) {
        groups(Validate.notBlank(column));
        return this;
    }

    public SQLQueryBuilder groups(String... columns) {
        Arrays.stream(Validate.notNull(columns)).map(GroupItem::new).forEach(group::add);
        return this;
    }

    public SQLQueryBuilder group(GroupItem groupItem) {
        group.add(Validate.notNull(groupItem));
        return this;
    }

    public SQLQueryBuilder groups(List<GroupItem> groupItems) {
        group.addAll(Validate.notNull(groupItems));
        return this;
    }

    public SQLQueryBuilder clearGroups() {
        group.clear();
        return this;
    }

    public SQLQueryBuilder order(String column) {
        orders(Validate.notBlank(column));
        return this;
    }

    public SQLQueryBuilder order(String column, String sort) {
        order.add(OrderItem.builder().field(Validate.notBlank(column)).sort(Validate.notBlank(sort)).build());
        return this;
    }

    public SQLQueryBuilder order(OrderItem orderItem) {
        order.add(Validate.notNull(orderItem));
        return this;
    }

    public SQLQueryBuilder orders(List<OrderItem> orderItems) {
        order.addAll(Validate.notNull(orderItems));
        return this;
    }

    public SQLQueryBuilder orders(String... columns) {
        Arrays.stream(Validate.notNull(columns)).map(OrderItem::new).forEach(order::add);
        return this;
    }

    public SQLQueryBuilder clearOrders() {
        order.clear();
        return this;
    }

    public SQLQueryBuilder join(JoinItem joinItem) {
        join.add(Validate.notNull(joinItem));
        return this;
    }

    public SQLQueryBuilder joins(List<JoinItem> joinItems) {
        join.addAll(Validate.notNull(joinItems));
        return this;
    }

    public SQLQueryBuilder clearJoins() {
        join.clear();
        return this;
    }

    public SQLQueryBuilder limit(Integer limit) {
        if(limit != null){
            Validate.isTrue(limit > 0, "limit should > 0");
        }
        this.limit = limit;
        return this;
    }

    public SQLQueryBuilder offset(Integer offset) {
       if(offset != null){
         Validate.isTrue(offset >= 0, "offset should >= 0");
       }
        this.offset = offset;
        return this;
    }



    public SQLQuery build() {
        Validate.isTrue(StringUtils.isNotBlank(from), "from should be not null");
        Validate.notEmpty(select, "select must have values");
        GenericSQLQuery sqlQuery = new GenericSQLQuery(select, from);

        sqlQuery.setWhere(where);
        sqlQuery.setJoin(join);
        sqlQuery.setGroup(group);
        sqlQuery.setHaving(having);
        sqlQuery.setOrder(order);
        sqlQuery.setLimit(limit);
        sqlQuery.setOffset(offset);
        return sqlQuery;
    }

    public static SQLQuery buildCountSQLQuery(SQLQuery query) {
        GenericSQLQuery countQuery = null;
        if (query instanceof GenericSQLQuery) {
            countQuery = (GenericSQLQuery) query.clone();
            countQuery.setSelect(Arrays.asList(SelectItem.defaultSelectCountItem()));
            countQuery.setOffset(0);
            countQuery.setLimit(1);
        }
        return countQuery;

    }

}
