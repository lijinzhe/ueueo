package com.weiming.framework.querydsl.core.model;



public class DefaultQueryItem implements QueryItem {
    private String field;
    private String as;
    private String func;

    public DefaultQueryItem(String field, String as, String func) {
        this.field = field;
        this.as = as;
        this.func = func;
    }

    public String getField() {
        return field;
    }

    public String getAs() {
        return as;
    }

    public String getFunc() {
        return func;
    }

    public static DefaultQueryItem with(String field) {
        return new DefaultQueryItem(field, null, null);
    }

    public static DefaultQueryItem with(String field, String as) {
        return new DefaultQueryItem(field, as, null);
    }

    public static DefaultQueryItem with(String field, String as, String func) {
        return new DefaultQueryItem(field, as, func);
    }
}
