package com.weiming.framework.querydsl.core;

import java.util.function.BiFunction;

@FunctionalInterface
public interface FunctionExpression extends BiFunction<String, String, String> {

}
