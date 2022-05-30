using System;
using System.Collections.Generic;

namespace Volo.Abp.Localization.ExceptionHandling;

public class AbpExceptionLocalizationOptions
{
    public Dictionary<String, Type> ErrorCodeNamespaceMappings;//  { get; }

    public AbpExceptionLocalizationOptions()
    {
        ErrorCodeNamespaceMappings = new Dictionary<String, Type>();
    }

    public void MapCodeNamespace(String errorCodeNamespace, Type type)
    {
        ErrorCodeNamespaceMappings[errorCodeNamespace] = type;
    }
}
