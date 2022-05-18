package com.ueueo.dependencyinjection.system;

/**
 * C# Microsoft.Extensions.DependencyInjection.IServiceScope
 *
 * The System.IDisposable.Dispose method ends the scope lifetime. Once Dispose is
 * called, any scoped services that have been resolved from Microsoft.Extensions.DependencyInjection.IServiceScope.ServiceProvider
 * will be disposed.
 *
 * @author Lee
 * @date 2022-05-18 10:37
 */
public interface IServiceScope {
    /**
     * The System.IServiceProvider used to resolve dependencies from the scope.
     *
     * @return
     */
    IServiceProvider getServiceProvider();
}
