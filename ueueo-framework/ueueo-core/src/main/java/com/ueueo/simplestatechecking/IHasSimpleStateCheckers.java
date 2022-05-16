package com.ueueo.simplestatechecking;

import java.util.List;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 15:13
 */
public interface IHasSimpleStateCheckers<TState extends IHasSimpleStateCheckers<TState>> {

    List<ISimpleStateChecker<TState>> stateCheckers();
}
