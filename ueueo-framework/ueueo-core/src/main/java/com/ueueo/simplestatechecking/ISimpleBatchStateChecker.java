package com.ueueo.simplestatechecking;

/**
 * @author Lee
 * @date 2021-08-26 15:20
 */
public interface ISimpleBatchStateChecker<TState extends IHasSimpleStateCheckers<TState>> extends ISimpleStateChecker<TState> {

    SimpleStateCheckerResult<TState> isEnabled(SimpleBatchStateCheckerContext<TState> context);
}
