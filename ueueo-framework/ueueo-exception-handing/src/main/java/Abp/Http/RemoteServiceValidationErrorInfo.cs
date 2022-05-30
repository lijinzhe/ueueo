using System;

namespace Volo.Abp.Http;

/**
 * Used to store information about a validation error.
*/
[Serializable]
public class RemoteServiceValidationErrorInfo
{
    /**
     * Validation error message.
    */
    public String Message;// { get; set; }

    /**
     * Relate invalid members (fields/properties).
    */
    public String[] Members;// { get; set; }

    /**
     * Creates a new instance of <see cref="RemoteServiceValidationErrorInfo"/>.
    */
    public RemoteServiceValidationErrorInfo()
    {

    }

    /**
     * Creates a new instance of <see cref="RemoteServiceValidationErrorInfo"/>.
    *
     * <param name="message">Validation error message</param>
     */
    public RemoteServiceValidationErrorInfo(String message)
    {
        Message = message;
    }

    /**
     * Creates a new instance of <see cref="RemoteServiceValidationErrorInfo"/>.
    *
     * <param name="message">Validation error message</param>
     * <param name="members">Related invalid members</param>
     */
    public RemoteServiceValidationErrorInfo(String message, String[] members)
        : this(message)
    {
        Members = members;
    }

    /**
     * Creates a new instance of <see cref="RemoteServiceValidationErrorInfo"/>.
    *
     * <param name="message">Validation error message</param>
     * <param name="member">Related invalid member</param>
     */
    public RemoteServiceValidationErrorInfo(String message, String member)
        : this(message, new[] { member })
    {

    }
}
