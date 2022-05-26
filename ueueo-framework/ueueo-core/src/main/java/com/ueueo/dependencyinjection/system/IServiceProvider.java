package com.ueueo.dependencyinjection.system;

/**
 * C# System.IServiceProvider
 * Defines a mechanism for retrieving a service object; that is, an object that
 * provides custom support to other objects.
 *
 * @author Lee
 * @date 2022-05-18 10:29
 */
public interface IServiceProvider {
    /**
     * Gets the service object of the specified type.
     *
     * @param serviceType An object that specifies the type of service object to get.
     *
     * @return A service object of type serviceType. -or- null if there is no service object
     * of type serviceType.
     */
    <T> T getService(Class<T> serviceType);

    /**
     * Get service of type serviceType from the System.IServiceProvider.
     *
     * @param serviceType An object that specifies the type of service object to get.
     *
     * @return A service object of type serviceType.
     *
     * @throws RuntimeException There is no service of type serviceType.
     */
    <T> T getRequiredService(Class<T> serviceType) throws RuntimeException;




}
