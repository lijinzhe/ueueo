package com.ueueo.simplestatechecking;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author Lee
 * @date 2021-08-26 15:22
 */
public class SimpleStateCheckerResult<TState extends IHasSimpleStateCheckers<TState>> extends HashMap<TState, Boolean> {
    public SimpleStateCheckerResult() {}

    public SimpleStateCheckerResult(TState... states) {
        this(Arrays.asList(states), true);
    }

    public SimpleStateCheckerResult(List<TState> states) {
        this(states, true);
    }

    public SimpleStateCheckerResult(List<TState> states, Boolean initValue) {
        for (TState state : states) {
            put(state, initValue);
        }
    }
}
