package com.ueueo.simplestatechecking;

import java.util.List;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 15:24
 */
public class SimpleBatchStateCheckerContext<TState extends IHasSimpleStateCheckers<TState>> {
    private List<TState> states;

    public SimpleBatchStateCheckerContext(List<TState> states) {
        this.states = states;
    }

    public List<TState> getStates() {
        return states;
    }
}
