package com.weiming.framework.querydsl.sql.model;

import com.weiming.framework.querydsl.core.model.ConditionItem;
import com.weiming.framework.querydsl.core.model.JoinOperator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Optional;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JoinItem implements Serializable {
    private String type;
    private String from;
    private ConditionItem on;

    public String getType() {
        return Optional.ofNullable(type).orElse(JoinOperator.LEFT.getOp());
    }

}
