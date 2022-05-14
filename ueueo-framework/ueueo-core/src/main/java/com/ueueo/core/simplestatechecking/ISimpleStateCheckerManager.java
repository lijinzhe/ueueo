package com.ueueo.core.simplestatechecking;

import java.util.List;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 15:25
 */
public interface ISimpleStateCheckerManager<TState extends IHasSimpleStateCheckers<TState>> {
    Boolean isEnabled(TState state);

    SimpleStateCheckerResult<TState> isEnabled(List<TState> states);
}
