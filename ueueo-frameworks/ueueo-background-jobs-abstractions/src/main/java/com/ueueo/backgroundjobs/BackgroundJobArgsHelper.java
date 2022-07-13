package com.ueueo.backgroundjobs;

public   class BackgroundJobArgsHelper
{
    public static Class<?> getJobArgsType(Class<?> jobType)
    {
        //TODO
//        for (Class<?> _interface : jobType.getInterfaces())
//        {
//            if (!_interface.getG())
//            {
//                continue;
//            }
//
//            if (_interface.GetGenericTypeDefinition() != typeof(IBackgroundJob<>) &&
//            _interface.GetGenericTypeDefinition() != typeof(IAsyncBackgroundJob<>))
//            {
//                continue;
//            }
//
//            var genericArgs =_interface.GetGenericArguments();
//            if (genericArgs.Length != 1)
//            {
//                continue;
//            }
//
//            return genericArgs[0];
//        }
//
//        throw new AbpException($"Could not find type of the job args. " +
//                               $"Ensure that given type implements the {typeof(IBackgroundJob<>).AssemblyQualifiedName} or {typeof(IAsyncBackgroundJob<>).AssemblyQualifiedName} interface. " +
//                               $"Given job type: {jobType.AssemblyQualifiedName}");
        return null;
    }
}
