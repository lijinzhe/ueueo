package com.ueueo.validation;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Container class for the results of a validation request.
 * Use the static <see cref="ValidationResult.Success" /> to represent successful validation.
 * <seealso cref="ValidationAttribute.GetValidationResult" />
 *
 * @author Lee
 * @date 2022-05-25 15:37
 */
public class ValidationResult {

    /**
     * Gets a <see cref="ValidationResult" /> that indicates Success.
     *
     * The <c>null</c> value is used to indicate success.  Consumers of <see cref="ValidationResult" />s
     * should compare the values to <see cref="ValidationResult.Success" /> rather than checking for null.
     */
    public static ValidationResult Success;

    /**
     * Constructor that accepts an error message.  This error message would override any error message
     * provided on the <see cref="ValidationAttribute" />.
     *
     * @param errorMessage The user-visible error message.  If null, <see cref="ValidationAttribute.GetValidationResult" />
     *                     will use <see cref="ValidationAttribute.FormatErrorMessage" /> for its error message.
     */
    public ValidationResult(CharSequence errorMessage) {
        this(errorMessage, null);
    }

    /**
     * Constructor that accepts an error message as well as a list of member names involved in the validation.
     * This error message would override any error message provided on the <see cref="ValidationAttribute" />.
     *
     * @param errorMessage The user-visible error message.  If null, <see cref="ValidationAttribute.GetValidationResult" />
     *                     will use <see cref="ValidationAttribute.FormatErrorMessage" /> for its error message.
     * @param memberNames  The list of member names affected by this result.
     *                     This list of member names is meant to be used by presentation layers to indicate which fields are in error.
     */
    public ValidationResult(CharSequence errorMessage, Collection<CharSequence> memberNames) {
        this.errorMessage = errorMessage;
        this.memberNames = memberNames != null ? memberNames : new ArrayList<>();
    }

    /**
     * Constructor that creates a copy of an existing ValidationResult.
     *
     * @param validationResult The validation result.
     *
     * @throws ValidationResult is null.
     */
    protected ValidationResult(ValidationResult validationResult) {
        if (validationResult == null) {
            throw new IllegalArgumentException("validationResult");
        }

        errorMessage = validationResult.errorMessage;
        memberNames = validationResult.memberNames;
    }

    /**
     * Gets the collection of member names affected by this result.  The collection may be empty but will never be null.
     */
    @Getter
    public Collection<CharSequence> memberNames;

    /**
     * Gets the error message for this result.  It may be null.
     */
    @Getter
    @Setter
    public CharSequence errorMessage;

    /**
     * Override the string representation of this instance, returning
     * the <see cref="ErrorMessage" /> if not <c>null</c>, otherwise
     * the base <see cref="Object.ToString" /> result.
     *
     * If the <see cref="ErrorMessage" /> is empty, it will still qualify
     * as being specified, and therefore returned from <see cref="ToString" />.
     *
     * @return The <see cref="ErrorMessage" /> property value if specified,
     * otherwise, the base <see cref="Object.ToString" /> result.
     */
    @Override
    public String toString() {
        if (StringUtils.isNotBlank(errorMessage)) {
            return errorMessage.toString();
        } else {
            return super.toString();
        }
    }
}
