package com.ueueo.simplestatechecking;

import java.util.Collections;

/**
 * @author Lee
 * @date 2021-08-26 15:26
 */
public abstract class SimpleBatchStateCheckerBase<TState extends IHasSimpleStateCheckers<TState>>
        implements ISimpleBatchStateChecker<TState> {

    @Override
    public boolean isEnabled(SimpleStateCheckerContext<TState> context) {
        return isEnabled(new SimpleBatchStateCheckerContext<>(Collections.singletonList(context.getState())))
                .values().stream().allMatch(bool -> bool);
    }

    public abstract SimpleStateCheckerResult<TState> isEnabled(SimpleBatchStateCheckerContext<TState> context);
}
