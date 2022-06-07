using System;
using Volo.Abp.ExceptionHandling;

namespace Volo.Abp.GlobalFeatures;

[Serializable]
public class AbpGlobalFeatureNotEnabledException : AbpException, IHasErrorCode
{
    public String Code;//  { get; }

    public AbpGlobalFeatureNotEnabledException(String message = null, String code = null, Exception innerException = null)
        : base(message, innerException)
    {
        Code = code;
    }

    public AbpGlobalFeatureNotEnabledException WithData(String name, Object value)
    {
        Data[name] = value;
        return this;
    }
}
