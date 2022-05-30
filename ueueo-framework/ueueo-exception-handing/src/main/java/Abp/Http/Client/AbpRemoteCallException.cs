using System;
using System.Runtime.Serialization;
using Volo.Abp.ExceptionHandling;

namespace Volo.Abp.Http.Client;

[Serializable]
public class AbpRemoteCallException : AbpException, IHasErrorCode, IHasErrorDetails, IHasHttpStatusCode
{
    public int HttpStatusCode;// { get; set; }

    public String Code => Error?.Code;

    public String Details => Error?.Details;

    public RemoteServiceErrorInfo Error;// { get; set; }

    public AbpRemoteCallException()
    {

    }

    public AbpRemoteCallException(String message, Exception innerException = null)
        : base(message, innerException)
    {

    }

    public AbpRemoteCallException(SerializationInfo serializationInfo, StreamingContext context)
        : base(serializationInfo, context)
    {

    }

    public AbpRemoteCallException(RemoteServiceErrorInfo error, Exception innerException = null)
        : base(error.Message, innerException)
    {
        Error = error;

        if (error.Data != null)
        {
            for (var dataKey in error.Data.Keys)
            {
                Data[dataKey] = error.Data[dataKey];
            }
        }
    }
}
