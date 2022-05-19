package com.weiming.framework.querydsl.core.generator;

import java.util.Collections;
import java.util.List;

public class DelegatingFunctionGenerator implements FunctionGenerator {

    private final List<FunctionGenerator> generators;

    public DelegatingFunctionGenerator(List<FunctionGenerator> generators) {
        this.generators = Collections.unmodifiableList(generators);
    }


    @Override
    public boolean supports(String function) {
        return generators.stream().anyMatch(generator -> generator.supports(function));
    }

    @Override
    public String generate(String function, String param) {
        return generators.stream().filter(generator -> generator.supports(function)).findFirst()
                .map(generator -> generator.generate(function, param)).orElseGet(() ->"");
    }
}
