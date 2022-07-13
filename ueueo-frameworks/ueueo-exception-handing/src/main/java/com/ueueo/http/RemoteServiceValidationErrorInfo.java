package com.ueueo.http;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * Used to store information about a validation error.
 */
@Data
public class RemoteServiceValidationErrorInfo {
    /**
     * Validation error message.
     */
    private String message;

    /**
     * Relate invalid members (fields/properties).
     */
    private List<String> members;

    /**
     * Creates a new instance of <see cref="RemoteServiceValidationErrorInfo"/>.
     */
    public RemoteServiceValidationErrorInfo() {

    }

    /**
     * Creates a new instance of <see cref="RemoteServiceValidationErrorInfo"/>.
     *
     * <param name="message">Validation error message</param>
     */
    public RemoteServiceValidationErrorInfo(String message) {
        this.message = message;
    }

    /**
     * Creates a new instance of <see cref="RemoteServiceValidationErrorInfo"/>.
     *
     * <param name="message">Validation error message</param>
     * <param name="members">Related invalid members</param>
     */
    public RemoteServiceValidationErrorInfo(String message, String... members) {
        this(message);
        this.members = Arrays.asList(members);
    }

}
