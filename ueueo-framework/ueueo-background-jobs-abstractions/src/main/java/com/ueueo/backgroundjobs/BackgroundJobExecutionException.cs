using System;
using System.Runtime.Serialization;

namespace Volo.Abp.BackgroundJobs;

[Serializable]
public class BackgroundJobExecutionException : AbpException
{
    public String JobType;// { get; set; }

    public Object JobArgs;// { get; set; }

    public BackgroundJobExecutionException()
    {

    }

    /**
     * Creates a new <see cref="BackgroundJobExecutionException"/> object.
    */
    public BackgroundJobExecutionException(SerializationInfo serializationInfo, StreamingContext context)
        : base(serializationInfo, context)
    {

    }

    /**
     * Creates a new <see cref="BackgroundJobExecutionException"/> object.
    *
     * <param name="message">Exception message</param>
     * <param name="innerException">Inner exception</param>
     */
    public BackgroundJobExecutionException(String message, Exception innerException)
        : base(message, innerException)
    {

    }
}
