package com.weiming.framework.querydsl.sql.model;

import com.weiming.framework.querydsl.core.model.FieldItem;
import com.weiming.framework.querydsl.core.model.OrderOperator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Optional;

@Getter
@Builder
@NoArgsConstructor
public class OrderItem implements FieldItem, Serializable {
    private String field;
    /**
     * 为null的时候，默认返回 DESC
     */
    private String sort;
    public OrderItem(String field) {
        this.field = field;
    }

    public OrderItem(String field, String sort) {
        this.field = field;
        this.sort = sort;
    }

    public String getSort() {
        return Optional.ofNullable(sort).orElse(OrderOperator.DESC.getOp());
    }

    public static OrderItem with(String field) {
        return new OrderItem(field);
    }

    public static OrderItem with(String field, String sort) {
        return new OrderItem(field, sort);
    }
}
