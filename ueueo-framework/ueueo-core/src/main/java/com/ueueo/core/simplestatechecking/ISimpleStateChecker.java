package com.ueueo.core.simplestatechecking;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 15:14
 */
public interface ISimpleStateChecker<TState extends IHasSimpleStateCheckers<TState>> {
    Boolean isEnabled(SimpleStateCheckerContext<TState> context);
}
