using System;
using System.Collections.Generic;
using JetBrains.Annotations;
using Volo.Abp.Http.Modeling;

namespace Volo.Abp.Http.Client.ClientProxying;

public class ClientProxyRequestContext
{
    [NotNull]
    public ActionApiDescriptionModel Action;//  { get; }

    [NotNull]
    public IReadOnlyDictionary<String, Object> Arguments;//  { get; }

    [NotNull]
    public Type ServiceType;//  { get; }

    public ClientProxyRequestContext(
        @NonNull ActionApiDescriptionModel action,
        @NonNull IReadOnlyDictionary<String, Object> arguments,
        @NonNull Type serviceType)
    {
        ServiceType = serviceType;
        Action = Objects.requireNonNull(action, nameof(action));
        Arguments = Objects.requireNonNull(arguments, nameof(arguments));
        ServiceType = Objects.requireNonNull(serviceType, nameof(serviceType));
    }
}
