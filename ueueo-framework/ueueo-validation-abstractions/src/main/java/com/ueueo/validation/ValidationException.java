package com.ueueo.validation;

import com.ueueo.exception.SystemException;
import com.ueueo.data.annotations.ValidationResult;
import com.ueueo.logging.IExceptionWithSelfLogging;
import com.ueueo.logging.IHasLogLevel;
import com.ueueo.logging.LoggerExtensions;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.event.Level;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Lee
 * @date 2022-05-25 22:11
 */
public class ValidationException extends SystemException implements IHasLogLevel, IHasValidationErrors, IExceptionWithSelfLogging {
    /**
     * Detailed list of validation errors for this exception.
     */
    @Getter
    private Collection<ValidationResult> ValidationErrors;

    /**
     * Exception severity.
     * Default: Warn.
     */
    @Getter
    @Setter
    public Level LogLevel;

    public ValidationException() {
        ValidationErrors = new ArrayList<>();
        LogLevel = Level.WARN;
    }

    public ValidationException(String message) {
        super(message);
        ValidationErrors = new ArrayList<>();
        LogLevel = Level.WARN;
    }

    public ValidationException(Collection<ValidationResult> validationErrors) {
        ValidationErrors = validationErrors;
        LogLevel = Level.WARN;
    }

    public ValidationException(String message, Collection<ValidationResult> validationErrors) {
        super(message);
        ValidationErrors = validationErrors;
        LogLevel = Level.WARN;
    }

    public ValidationException(String message, Exception innerException) {
        super(message, innerException);
        ValidationErrors = new ArrayList<>();
        LogLevel = Level.WARN;
    }

    @Override
    public void log(Logger logger) {
        if (ValidationErrors == null || ValidationErrors.isEmpty()) {
            return;
        }
        StringBuilder validationErrorsString = new StringBuilder()
                .append("There are ")
                .append(ValidationErrors.size())
                .append(" validation errors:")
                .append(StringUtils.LF);
        for (ValidationResult validationResult : ValidationErrors) {
            String memberNames = "";
            if (CollectionUtils.isNotEmpty(validationResult.getMemberNames())) {
                memberNames = " (" + StringUtils.join(validationResult.getMemberNames(), ", ") + ")";
            }
            validationErrorsString.append(validationResult.getErrorMessage())
                    .append(memberNames)
                    .append(StringUtils.LF);
        }
        LoggerExtensions.logWithLevel(logger, LogLevel, validationErrorsString.toString());
    }
}
