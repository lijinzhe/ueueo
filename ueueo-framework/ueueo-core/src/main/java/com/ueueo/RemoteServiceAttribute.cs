using System;
using System.Reflection;

namespace Volo.Abp;

[Serializable]
[AttributeUsage(AttributeTargets.Interface | AttributeTargets.Class | AttributeTargets.Method)]
public class RemoteServiceAttribute : Attribute //TODO: Can we move this to another package (with IRemoteService)?
{
    /**
     * Default: true.
    */
    public boolean IsEnabled;// { get; set; }

    /**
     * Default: true.
    */
    public boolean IsMetadataEnabled;// { get; set; }

    /**
     * Group name of the remote service.
     * Group names of all services of a module expected to be the same.
     * This name is also used to distinguish the service endpoint of this group.
    */
    public String Name;// { get; set; }

    public RemoteServiceAttribute(boolean isEnabled = true)
    {
        IsEnabled = isEnabled;
        IsMetadataEnabled = true;
    }

    public   boolean IsEnabledFor(Type type)
    {
        return IsEnabled;
    }

    public   boolean IsEnabledFor(MethodInfo method)
    {
        return IsEnabled;
    }

    public   boolean IsMetadataEnabledFor(Type type)
    {
        return IsMetadataEnabled;
    }

    public   boolean IsMetadataEnabledFor(MethodInfo method)
    {
        return IsMetadataEnabled;
    }

    public static boolean IsExplicitlyEnabledFor(Type type)
    {
        var remoteServiceAttr = type.GetTypeInfo().GetSingleAttributeOrNull<RemoteServiceAttribute>();
        return remoteServiceAttr != null && remoteServiceAttr.IsEnabledFor(type);
    }

    public static boolean IsExplicitlyDisabledFor(Type type)
    {
        var remoteServiceAttr = type.GetTypeInfo().GetSingleAttributeOrNull<RemoteServiceAttribute>();
        return remoteServiceAttr != null && !remoteServiceAttr.IsEnabledFor(type);
    }

    public static boolean IsMetadataExplicitlyEnabledFor(Type type)
    {
        var remoteServiceAttr = type.GetTypeInfo().GetSingleAttributeOrNull<RemoteServiceAttribute>();
        return remoteServiceAttr != null && remoteServiceAttr.IsMetadataEnabledFor(type);
    }

    public static boolean IsMetadataExplicitlyDisabledFor(Type type)
    {
        var remoteServiceAttr = type.GetTypeInfo().GetSingleAttributeOrNull<RemoteServiceAttribute>();
        return remoteServiceAttr != null && !remoteServiceAttr.IsMetadataEnabledFor(type);
    }

    public static boolean IsMetadataExplicitlyDisabledFor(MethodInfo method)
    {
        var remoteServiceAttr = method.GetSingleAttributeOrNull<RemoteServiceAttribute>();
        return remoteServiceAttr != null && !remoteServiceAttr.IsMetadataEnabledFor(method);
    }

    public static boolean IsMetadataExplicitlyEnabledFor(MethodInfo method)
    {
        var remoteServiceAttr = method.GetSingleAttributeOrNull<RemoteServiceAttribute>();
        return remoteServiceAttr != null && remoteServiceAttr.IsMetadataEnabledFor(method);
    }
}
