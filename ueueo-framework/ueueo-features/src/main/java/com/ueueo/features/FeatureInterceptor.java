package com.ueueo.features;

import com.ueueo.aspects.AbpCrossCuttingConcerns;
import com.ueueo.dynamicproxy.AbpInterceptor;
import com.ueueo.dynamicproxy.IAbpMethodInvocation;

/**
 * @author Lee
 * @date 2022-05-17 16:54
 */
public class FeatureInterceptor extends AbpInterceptor {

    private final IMethodInvocationFeatureCheckerService checkerService;

    public FeatureInterceptor(IMethodInvocationFeatureCheckerService checkerService) {
        this.checkerService = checkerService;
    }

    @Override
    public void intercept(IAbpMethodInvocation invocation) {
        if (AbpCrossCuttingConcerns.isApplied(invocation.getTargetObject(), AbpCrossCuttingConcerns.FeatureChecking)) {
            invocation.proceed();
        }
        checkFeatures(invocation);
        invocation.proceed();
    }

    protected void checkFeatures(IAbpMethodInvocation invocation) {
        checkerService.check(new MethodInvocationFeatureCheckerContext(invocation.getMethod()));
    }
}
