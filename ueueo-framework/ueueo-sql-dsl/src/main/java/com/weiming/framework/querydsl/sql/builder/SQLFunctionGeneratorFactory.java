package com.weiming.framework.querydsl.sql.builder;

import com.weiming.framework.querydsl.core.generator.DefaultFunctionGenerator;
import com.weiming.framework.querydsl.core.generator.DelegatingFunctionGenerator;
import com.weiming.framework.querydsl.core.generator.FunctionGenerator;

import java.util.Arrays;

public class SQLFunctionGeneratorFactory {
    public static FunctionGenerator createDefaultSQLFunctionGenerator() {
        return new DelegatingFunctionGenerator(Arrays.asList(
                new DefaultFunctionGenerator("COUNT"),
                new DefaultFunctionGenerator("SUM"),
                new DefaultFunctionGenerator("AVG"),
                new DefaultFunctionGenerator("MAX"),
                new DefaultFunctionGenerator("MIN")
        ));
    }
}
