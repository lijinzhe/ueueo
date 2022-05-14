package com.ueueo.core.simplestatechecking;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 15:14
 */
public class SimpleStateCheckerContext<TState extends IHasSimpleStateCheckers<TState>> {

    private TState state ;

    public SimpleStateCheckerContext(TState state){
        this.state = state;
    }

    public TState getState() {
        return state;
    }
}
