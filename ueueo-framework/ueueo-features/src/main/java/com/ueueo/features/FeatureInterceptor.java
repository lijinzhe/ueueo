package com.ueueo.features;

import com.ueueo.aspects.AbpCrossCuttingConcerns;
import com.ueueo.dependencyinjection.system.IServiceScope;
import com.ueueo.dependencyinjection.system.IServiceScopeFactory;
import com.ueueo.dynamicproxy.AbpInterceptor;
import com.ueueo.dynamicproxy.IAbpMethodInvocation;

/**
 * @author Lee
 * @date 2022-05-17 16:54
 */
public class FeatureInterceptor extends AbpInterceptor {

    private final IServiceScopeFactory serviceScopeFactory;

    public FeatureInterceptor(IServiceScopeFactory serviceScopeFactory) {
        this.serviceScopeFactory = serviceScopeFactory;
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
        IServiceScope serviceScope = serviceScopeFactory.createScope();
        IMethodInvocationFeatureCheckerService checker = serviceScope.getServiceProvider().getRequiredService(IMethodInvocationFeatureCheckerService.class);
        checker.check(new MethodInvocationFeatureCheckerContext(invocation.getMethod()));
    }
}
