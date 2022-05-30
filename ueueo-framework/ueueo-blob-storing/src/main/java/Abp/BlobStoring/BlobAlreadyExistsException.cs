using System;
using System.Runtime.Serialization;

namespace Volo.Abp.BlobStoring;

public class BlobAlreadyExistsException : AbpException
{
    public BlobAlreadyExistsException()
    {

    }

    public BlobAlreadyExistsException(String message)
        : base(message)
    {

    }

    public BlobAlreadyExistsException(String message, Exception innerException)
        : base(message, innerException)
    {

    }

    public BlobAlreadyExistsException(SerializationInfo serializationInfo, StreamingContext context)
        : base(serializationInfo, context)
    {

    }
}
