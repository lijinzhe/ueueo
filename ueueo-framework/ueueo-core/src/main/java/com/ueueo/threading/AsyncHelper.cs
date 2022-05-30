using System;
using System.Reflection;
using System.Threading.Tasks;
using JetBrains.Annotations;
using Nito.AsyncEx;

namespace Volo.Abp.Threading;

/**
 * Provides some helper methods to work with  methods.
*/
public static class AsyncHelper
{
    /**
     * Checks if given method is an  method.
    *
     * <param name="method">A method to check</param>
     */
    public static boolean IsAsync(@Nonnull this MethodInfo method)
    {
        Objects.requireNonNull(method, nameof(method));

        return method.ReturnType.IsTaskOrTaskOfT();
    }

    public static boolean IsTaskOrTaskOfT(@Nonnull this Type type)
    {
        return type == typeof(Task) || (type.GetTypeInfo().IsGenericType && type.GetGenericTypeDefinition() == typeof(Task<>));
    }

    public static boolean IsTaskOfT(@Nonnull this Type type)
    {
        return type.GetTypeInfo().IsGenericType && type.GetGenericTypeDefinition() == typeof(Task<>);
    }

    /**
     * Returns void if given type is Task.
     * Return T, if given type is Task{T}.
     * Returns given type otherwise.
    */
    public static Type UnwrapTask(@Nonnull Type type)
    {
        Objects.requireNonNull(type, nameof(type));

        if (type == typeof(Task))
        {
            return typeof(void);
        }

        if (type.IsTaskOfT())
        {
            return type.GenericTypeArguments[0];
        }

        return type;
    }

    /**
     * Runs a  method synchronously.
    *
     * <param name="func">A function that returns a result</param>
     * <typeparam name="TResult">Result type</typeparam>
     * <returns>Result of the  operation</returns>
     */
    public static TResult RunSync<TResult>(Func<Task<TResult>> func)
    {
        return AsyncContext.Run(func);
    }

    /**
     * Runs a  method synchronously.
    *
     * <param name="action">An  action</param>
     */
    public static void RunSync(Func<Task> action)
    {
        AsyncContext.Run(action);
    }
}
