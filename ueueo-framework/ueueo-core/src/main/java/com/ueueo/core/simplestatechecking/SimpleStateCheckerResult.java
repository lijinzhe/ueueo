package com.ueueo.core.simplestatechecking;

import java.util.HashMap;
import java.util.List;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 15:22
 */
public class SimpleStateCheckerResult<TState extends IHasSimpleStateCheckers<TState>> extends HashMap<TState, Boolean> {
    public SimpleStateCheckerResult() {

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
