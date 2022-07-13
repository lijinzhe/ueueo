package com.ueueo.data;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lee
 * @date 2022-05-29 14:48
 */
@Data
public class AbpDataFilterOptions {
    private Map<Class<?>, DataFilterState> defaultStates;

    public AbpDataFilterOptions() {
        defaultStates = new HashMap<>();
    }
}
