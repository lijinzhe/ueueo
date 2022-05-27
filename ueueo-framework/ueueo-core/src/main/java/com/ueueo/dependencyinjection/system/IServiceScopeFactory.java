//package com.ueueo.dependencyinjection.system;
//
///**
// * C# Microsoft.Extensions.DependencyInjection.IServiceScopeFactory
// *
// * A factory for creating instances of Microsoft.Extensions.DependencyInjection.IServiceScope,
// * which is used to create services within a scope.
// *
// * @author Lee
// * @date 2022-05-18 10:38
// */
//public interface IServiceScopeFactory {
//    /**
//     * Create an Microsoft.Extensions.DependencyInjection.IServiceScope which contains
//     * an System.IServiceProvider used to resolve dependencies from a newly created
//     * scope.
//     *
//     * @return An Microsoft.Extensions.DependencyInjection.IServiceScope controlling the lifetime
//     * of the scope. Once this is disposed, any scoped services that have been resolved
//     * from the Microsoft.Extensions.DependencyInjection.IServiceScope.ServiceProvider
//     * will also be disposed.
//     */
//    IServiceScope createScope();
//}
