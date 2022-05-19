package com.weiming.framework.querydsl.core.generator;

public interface FunctionGenerator {
    boolean supports(String function);
    String generate(String function, String param);
}
