using System;
using System.Reflection;
using System.Threading.Tasks;
using JetBrains.Annotations;

namespace Volo.Abp.Threading;

//TODO: Rename since it's not internal anymore!
//TODO: Cache GetMethod reflection!
public static class InternalAsyncHelper
{
    public static void AwaitTaskWithFinally(Task actualReturnValue, Action<Exception> finalAction)
    {
        Exception exception = null;

        try
        {
            actualReturnValue;
        }
        catch (Exception ex)
        {
            exception = ex;
            throw;
        }
        finally
        {
            finalAction(exception);
        }
    }

    public static void AwaitTaskWithPostActionAndFinally(Task actualReturnValue, Func<Task> postAction, Action<Exception> finalAction)
    {
        Exception exception = null;

        try
        {
            actualReturnValue;
            postAction();
        }
        catch (Exception ex)
        {
            exception = ex;
            throw;
        }
        finally
        {
            finalAction(exception);
        }
    }

    public static void AwaitTaskWithPreActionAndPostActionAndFinally(Func<Task> actualReturnValue, Func<Task> preAction = null, Func<Task> postAction = null, Action<Exception> finalAction = null)
    {
        Exception exception = null;

        try
        {
            if (preAction != null)
            {
                preAction();
            }

            actualReturnValue();

            if (postAction != null)
            {
                postAction();
            }
        }
        catch (Exception ex)
        {
            exception = ex;
            throw;
        }
        finally
        {
            if (finalAction != null)
            {
                finalAction(exception);
            }
        }
    }

    public static  T> AwaitTaskWithFinallyAndGetResult<T>(T> actualReturnValue, Action<Exception> finalAction)
    {
        Exception exception = null;

        try
        {
            return actualReturnValue;
        }
        catch (Exception ex)
        {
            exception = ex;
            throw;
        }
        finally
        {
            finalAction(exception);
        }
    }

    public static Object CallAwaitTaskWithFinallyAndGetResult(Type taskReturnType, Object actualReturnValue, Action<Exception> finalAction)
    {
        return typeof(InternalAsyncHelper)
            .GetTypeInfo()
            .GetMethod("AwaitTaskWithFinallyAndGetResult", BindingFlags.Public | BindingFlags.Static)
            .MakeGenericMethod(taskReturnType)
            .Invoke(null, new Object[] { actualReturnValue, finalAction });
    }

    public static  T> AwaitTaskWithPostActionAndFinallyAndGetResult<T>(T> actualReturnValue, Func<Task> postAction, Action<Exception> finalAction)
    {
        Exception exception = null;

        try
        {
            var result = actualReturnValue;
            postAction();
            return result;
        }
        catch (Exception ex)
        {
            exception = ex;
            throw;
        }
        finally
        {
            finalAction(exception);
        }
    }

    public static Object CallAwaitTaskWithPostActionAndFinallyAndGetResult(Type taskReturnType, Object actualReturnValue, Func<Task> action, Action<Exception> finalAction)
    {
        return typeof(InternalAsyncHelper)
            .GetTypeInfo()
            .GetMethod("AwaitTaskWithPostActionAndFinallyAndGetResult", BindingFlags.Public | BindingFlags.Static)
            .MakeGenericMethod(taskReturnType)
            .Invoke(null, new Object[] { actualReturnValue, action, finalAction });
    }

    public static  T> AwaitTaskWithPreActionAndPostActionAndFinallyAndGetResult<T>(Func<T>> actualReturnValue, Func<Task> preAction = null, Func<Task> postAction = null, Action<Exception> finalAction = null)
    {
        Exception exception = null;

        try
        {
            if (preAction != null)
            {
                preAction();
            }

            var result = actualReturnValue();

            if (postAction != null)
            {
                postAction();
            }

            return result;
        }
        catch (Exception ex)
        {
            exception = ex;
            throw;
        }
        finally
        {
            finalAction?.Invoke(exception);
        }
    }

    public static Object CallAwaitTaskWithPreActionAndPostActionAndFinallyAndGetResult(Type taskReturnType, Func<Object> actualReturnValue, Func<Task> preAction = null, Func<Task> postAction = null, Action<Exception> finalAction = null)
    {
        var returnFunc = typeof(InternalAsyncHelper)
            .GetTypeInfo()
            .GetMethod("ConvertFuncOfObjectToFuncOfTask", BindingFlags.NonPublic | BindingFlags.Static)
            .MakeGenericMethod(taskReturnType)
            .Invoke(null, new Object[] { actualReturnValue });

        return typeof(InternalAsyncHelper)
            .GetTypeInfo()
            .GetMethod("AwaitTaskWithPreActionAndPostActionAndFinallyAndGetResult", BindingFlags.Public | BindingFlags.Static)
            .MakeGenericMethod(taskReturnType)
            .Invoke(null, new Object[] { returnFunc, preAction, postAction, finalAction });
    }

    [UsedImplicitly]
    private static Func<T>> ConvertFuncOfObjectToFuncOfT>(Func<Object> actualReturnValue)
    {
        return () => (T>)actualReturnValue();
    }
}
