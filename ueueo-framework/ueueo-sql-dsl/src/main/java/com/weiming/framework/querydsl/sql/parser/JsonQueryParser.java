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
        return this.getObjectMapper().readValue(jsonQueryContent, GenericSQLQuery.class);
    }

    public List<SelectItem> parseSelect(String selectJson) throws JsonProcessingException {
        return this.getObjectMapper().readValue(selectJson, new TypeReference<List<SelectItem>>() {});
    }

    public List<WhereItem> parseWhere(String whereJson) throws JsonProcessingException {
        return this.getObjectMapper().readValue(whereJson, new TypeReference<List<WhereItem>>() {});
    }

    public List<JoinItem> parseJoin(String joinJson) throws JsonProcessingException {
        return this.getObjectMapper().readValue(joinJson, new TypeReference<List<JoinItem>>() {});
    }

    public List<GroupItem> parseGroup(String groupJson) throws JsonProcessingException {
        return this.getObjectMapper().readValue(groupJson, new TypeReference<List<GroupItem>>() {});
    }

    public List<OrderItem> parseOrder(String orderJson) throws JsonProcessingException {
        return this.getObjectMapper().readValue(orderJson, new TypeReference<List<OrderItem>>() {});
    }

}
