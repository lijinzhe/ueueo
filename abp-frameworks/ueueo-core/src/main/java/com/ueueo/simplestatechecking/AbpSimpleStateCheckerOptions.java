package com.ueueo.simplestatechecking;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lee
 * @date 2022-05-26 20:48
 */
@Getter
public class AbpSimpleStateCheckerOptions<TState extends IHasSimpleStateCheckers<TState>> {
    private List<Class<? extends ISimpleStateChecker<TState>>> globalStateCheckers;

    public AbpSimpleStateCheckerOptions() {
        globalStateCheckers = new ArrayList<>();
    }
}
