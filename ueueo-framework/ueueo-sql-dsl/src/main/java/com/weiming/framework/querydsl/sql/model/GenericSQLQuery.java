package com.weiming.framework.querydsl.sql.model;

import com.weiming.framework.querydsl.core.model.ConditionItem;
import com.weiming.framework.querydsl.core.model.QueryCondition;
import com.weiming.framework.querydsl.util.DslUtils;
import lombok.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Setter(value = AccessLevel.PACKAGE)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GenericSQLQuery implements SQLQuery {

    private List<SelectItem> select = new ArrayList<>();
    private String from;
    private List<ConditionItem> where = new ArrayList<>();
    private List<OrderItem> order = new ArrayList<>();
    private List<GroupItem> group = new ArrayList<>();
    private List<ConditionItem> having = new ArrayList<>();
    private List<JoinItem> join = new ArrayList<>();
    private Integer limit;
    private Integer offset;

    @Override
    public List<SelectItem> getSelect() {
        return DslUtils.unmodifiableListOrEmpty(select);
    }
    @Override
    public String getFrom() {
        return from;
    }
    @Override
    public List<QueryCondition> getWhere() {
        return DslUtils.unmodifiableListOrEmpty(where);
    }

    @Override
    public boolean hasWhere() {
        return CollectionUtils.isNotEmpty(where);
    }

    @Override
    public List<OrderItem> getOrder() {
        return DslUtils.unmodifiableListOrEmpty(order);
    }

    @Override
    public boolean hasOrder() {
        return CollectionUtils.isNotEmpty(order);
    }

    @Override
    public List<GroupItem> getGroup() {
        return DslUtils.unmodifiableListOrEmpty(group);
    }

    @Override
    public boolean hasGroup() {
        return CollectionUtils.isNotEmpty(group);
    }

    @Override
    public List<QueryCondition> getHaving() {
        return DslUtils.unmodifiableListOrEmpty(having);
    }

    @Override
    public boolean hasHaving() {
        return CollectionUtils.isNotEmpty(having);
    }

    @Override
    public List<JoinItem> getJoin() {
        return DslUtils.unmodifiableListOrEmpty(join);
    }

    @Override
    public boolean hasJoin() {
        return CollectionUtils.isNotEmpty(join);
    }

    @Override
    public Integer getLimit() {
        return limit;
    }

    @Override
    public boolean hasLimit() {
        return ObjectUtils.isNotEmpty(limit);
    }

    @Override
    public Integer getOffset() {
        return offset;
    }
    @Override
    public boolean hasOffset() {
        return ObjectUtils.isNotEmpty(offset);
    }

    public GenericSQLQuery(List<SelectItem> select, String from) {
        Validate.isTrue(StringUtils.isNotBlank(from), "from should be not null");
        Validate.notEmpty(select, "select must have values");
        this.select = select;
        this.from = from;
    }

    @Override
    public GenericSQLQuery clone() {
        GenericSQLQuery sqlQueryCopy = null;
        try {
            //将对象序列化到流里
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(this);
            //将流反序列化成对象
            ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(is);
            sqlQueryCopy = (this.getClass().cast(ois.readObject()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return sqlQueryCopy;
    }
}
