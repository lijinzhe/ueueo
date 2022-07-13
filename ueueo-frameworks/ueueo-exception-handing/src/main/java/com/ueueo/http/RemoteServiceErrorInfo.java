package com.ueueo.http;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Used to store information about an error.
 */
@Data
public class RemoteServiceErrorInfo {
    /**
     * Error code.
     */
    private String code;

    /**
     * Error message.
     */
    private String message;

    /**
     * Error details.
     */
    private String details;

    private Map<String,Object> data;

    /**
     * Validation errors if exists.
     */
    private List<RemoteServiceValidationErrorInfo> validationErrors;

    /**
     * Creates a new instance of <see cref="RemoteServiceErrorInfo"/>.
     */
    public RemoteServiceErrorInfo() {

    }

    /**
     * Creates a new instance of <see cref="RemoteServiceErrorInfo"/>.
     *
     * <param name="code">Error code</param>
     * <param name="details">Error details</param>
     * <param name="message">Error message</param>
     */
    public RemoteServiceErrorInfo(String message, String details, String code) {
        this.message = message;
        this.details = details;
        this.code = code;
    }
}
