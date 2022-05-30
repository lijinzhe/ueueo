using System;
using System.Collections;

namespace Volo.Abp.Http;

/**
 * Used to store information about an error.
*/
[Serializable]
public class RemoteServiceErrorInfo
{
    /**
     * Error code.
    */
    public String Code;// { get; set; }

    /**
     * Error message.
    */
    public String Message;// { get; set; }

    /**
     * Error details.
    */
    public String Details;// { get; set; }

    public IDictionary Data;// { get; set; }

    /**
     * Validation errors if exists.
    */
    public RemoteServiceValidationErrorInfo[] ValidationErrors;// { get; set; }

    /**
     * Creates a new instance of <see cref="RemoteServiceErrorInfo"/>.
    */
    public RemoteServiceErrorInfo()
    {

    }

    /**
     * Creates a new instance of <see cref="RemoteServiceErrorInfo"/>.
    *
     * <param name="code">Error code</param>
     * <param name="details">Error details</param>
     * <param name="message">Error message</param>
     */
    public RemoteServiceErrorInfo(String message, String details = null, String code = null)
    {
        Message = message;
        Details = details;
        Code = code;
    }
}
