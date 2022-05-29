package com.ueueo.ddd.domain;

/**
 * @author Lee
 * @date 2022-05-25 21:19
 */
public interface IHasConcurrencyStamp {
    String getConcurrencyStamp();

    void setConcurrencyStamp(String concurrencyStamp);
}
