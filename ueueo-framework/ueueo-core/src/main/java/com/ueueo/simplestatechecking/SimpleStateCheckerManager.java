package com.ueueo.simplestatechecking;

import java.util.List;

/**
 * TODO ABP代码
 *
 * @author Lee
 * @date 2021-08-26 15:39
 */
public class SimpleStateCheckerManager<TState extends IHasSimpleStateCheckers<TState>> implements ISimpleStateCheckerManager<TState> {
    //TODO by Lee on 2021-08-26 16:01 没有实现
    @Override
    public Boolean isEnabled(TState tState) {
        return null;
    }

    @Override
    public SimpleStateCheckerResult<TState> isEnabled(List<TState> tStates) {
        return null;
    }

}
