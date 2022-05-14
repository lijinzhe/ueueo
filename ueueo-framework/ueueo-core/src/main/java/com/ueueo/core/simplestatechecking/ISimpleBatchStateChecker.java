package com.ueueo.core.simplestatechecking;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 15:20
 */
public interface ISimpleBatchStateChecker<TState extends IHasSimpleStateCheckers<TState>> extends ISimpleStateChecker<TState> {
    SimpleStateCheckerResult<TState> IsEnabledAsync(SimpleBatchStateCheckerContext<TState> context);
}
