package com.weiming.framework.querydsl.sql.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weiming.framework.querydsl.sql.model.*;

import java.util.List;

public class JsonQueryParser {

    private ObjectMapper objectMapper;

    public JsonQueryParser() {
    }

    public JsonQueryParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private ObjectMapper getObjectMapper() {
        if (this.objectMapper == null) {
            this.objectMapper = new ObjectMapper();
        }
        return this.objectMapper;
    }

    public GenericSQLQuery parseSqlQuery(String jsonQueryContent) throws JsonProcessingException {

        GenericSQLQuery jsonQuery = this.getObjectMapper().readValue(jsonQueryContent, GenericSQLQuery.class);
        return jsonQuery;
    }

    public List<SelectItem> parseSelect(String selectJson) throws JsonProcessingException {
        List<SelectItem> selectItems = this.getObjectMapper().readValue(selectJson, new TypeReference<>() {});
        return selectItems;
    }

    public List<WhereItem> parseWhere(String whereJson) throws JsonProcessingException {
        List<WhereItem> whereItems = this.getObjectMapper().readValue(whereJson, new TypeReference<>() {});
        return whereItems;
    }

    public List<JoinItem> parseJoin(String joinJson) throws JsonProcessingException {
        List<JoinItem> joinItems = this.getObjectMapper().readValue(joinJson, new TypeReference<>() {});
        return joinItems;
    }

    public List<GroupItem> parseGroup(String groupJson) throws JsonProcessingException {
        List<GroupItem> groupItems = this.getObjectMapper().readValue(groupJson, new TypeReference<>() {});
        return groupItems;
    }

    public List<OrderItem> parseOrder(String orderJson) throws JsonProcessingException {
        List<OrderItem> orderItems = this.getObjectMapper().readValue(orderJson, new TypeReference<>() {});
        return orderItems;
    }

}
