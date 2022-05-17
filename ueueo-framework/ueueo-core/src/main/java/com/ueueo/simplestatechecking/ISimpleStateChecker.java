package com.ueueo.simplestatechecking;

/**
 * @author Lee
 * @date 2021-08-26 15:14
 */
public interface ISimpleStateChecker<TState extends IHasSimpleStateCheckers<TState>> {
    boolean isEnabled(SimpleStateCheckerContext<TState> context);
}
