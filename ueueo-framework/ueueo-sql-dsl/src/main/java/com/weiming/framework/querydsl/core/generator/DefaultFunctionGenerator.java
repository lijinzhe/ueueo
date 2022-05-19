package com.weiming.framework.querydsl.core.generator;

import com.weiming.framework.querydsl.core.FunctionExpression;

/**
 * 可以用来转换标准函数的与具体函数之间的转换
 */
public class DefaultFunctionGenerator implements FunctionGenerator {

    private String function;
    private FunctionExpression functionExpression = (f, p) -> f+"("+ p +")";


    public DefaultFunctionGenerator(String function) {
        this.function = function;
    }

    public DefaultFunctionGenerator(String function, FunctionExpression functionExpression) {
        this.function = function;
        this.functionExpression = functionExpression;
    }


    /**
     * 默认实现方案，忽略大小写判断
     * @param function
     * @return
     */
    @Override
    public boolean supports(String function) {
        return this.function.equalsIgnoreCase(function);
    }

    /**
     * 注意这里不要用参数传进来的function
     * @param function
     * @param param
     * @return
     */
    @Override
    public String generate(String function, String param) {
        return functionExpression.apply(this.function, param);
    }
}
