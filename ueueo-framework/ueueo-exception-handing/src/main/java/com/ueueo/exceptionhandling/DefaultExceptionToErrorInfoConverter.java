package com.ueueo.exceptionhandling;

import com.ueueo.exception.BusinessException;
import com.ueueo.exception.IUserFriendlyException;
import com.ueueo.authorization.AuthorizationException;
import com.ueueo.data.DbConcurrencyException;
import com.ueueo.data.annotations.ValidationResult;
import com.ueueo.domain.entities.EntityNotFoundException;
import com.ueueo.http.RemoteServiceErrorInfo;
import com.ueueo.http.RemoteServiceValidationErrorInfo;
import com.ueueo.http.client.RemoteCallException;
import com.ueueo.localization.ICurrentLocale;
import com.ueueo.localization.exceptionhandling.AbpExceptionLocalizationOptions;
import com.ueueo.validation.ValidationException;
import com.ueueo.validation.IHasValidationErrors;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.MessageSourceAccessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Data
public class DefaultExceptionToErrorInfoConverter implements IExceptionToErrorInfoConverter {
    protected AbpExceptionLocalizationOptions localizationOptions;
    protected MessageSourceAccessor messageSourceAccessor;
    private ICurrentLocale currentLocale;

    public DefaultExceptionToErrorInfoConverter(
            AbpExceptionLocalizationOptions localizationOptions,
            MessageSourceAccessor messageSourceAccessor) {
        this.messageSourceAccessor = messageSourceAccessor;
        this.localizationOptions = localizationOptions;
    }

    @Override
    public RemoteServiceErrorInfo convert(Exception exception, Consumer<AbpExceptionHandlingOptions> options) {
        AbpExceptionHandlingOptions exceptionHandlingOptions = createDefaultOptions();
        if (options != null) {
            options.accept(exceptionHandlingOptions);
        }

        RemoteServiceErrorInfo errorInfo = createErrorInfoWithoutCode(exception, exceptionHandlingOptions);
        if (exception instanceof IHasErrorCode) {
            errorInfo.setCode(((IHasErrorCode) exception).getCode());
        }
        return errorInfo;
    }

    public RemoteServiceErrorInfo convert(Exception exception, boolean includeSensitiveDetails) {
        AbpExceptionHandlingOptions exceptionHandlingOptions = createDefaultOptions();
        exceptionHandlingOptions.setSendExceptionsDetailsToClients(includeSensitiveDetails);
        exceptionHandlingOptions.setSendStackTraceToClients(includeSensitiveDetails);

        RemoteServiceErrorInfo errorInfo = createErrorInfoWithoutCode(exception, exceptionHandlingOptions);
        if (exception instanceof IHasErrorCode) {
            errorInfo.setCode(((IHasErrorCode) exception).getCode());
        }
        return errorInfo;
    }

    protected RemoteServiceErrorInfo createErrorInfoWithoutCode(Exception exception, AbpExceptionHandlingOptions options) {
        if (options.isSendExceptionsDetailsToClients()) {
            return createDetailedErrorInfoFromException(exception, options.isSendExceptionsDetailsToClients());
        }

        exception = tryToGetActualException(exception);

        if (exception instanceof RemoteCallException && ((RemoteCallException) exception).getError() != null) {
            return ((RemoteCallException) exception).getError();
        }
        if (exception instanceof DbConcurrencyException) {
            return new RemoteServiceErrorInfo(messageSourceAccessor.getMessage("AbpDbConcurrencyErrorMessage"), null, null);
        }
        if (exception instanceof EntityNotFoundException) {
            return createEntityNotFoundError((EntityNotFoundException) exception);
        }

        RemoteServiceErrorInfo errorInfo = new RemoteServiceErrorInfo();
        if (exception instanceof IUserFriendlyException || exception instanceof RemoteCallException) {
            errorInfo.setMessage(exception.getMessage());
            if (exception instanceof IHasErrorDetails) {
                errorInfo.setDetails(((IHasErrorDetails) exception).getDetails());
            }
        }

        if (exception instanceof IHasValidationErrors) {
            if (StringUtils.isBlank(errorInfo.getMessage())) {
                errorInfo.setMessage(messageSourceAccessor.getMessage("ValidationErrorMessage"));
            }
            if (StringUtils.isBlank(errorInfo.getDetails())) {
                errorInfo.setDetails(getValidationErrorNarrative((IHasValidationErrors) exception));
            }
            errorInfo.setValidationErrors(getValidationErrorInfos((IHasValidationErrors) exception));
        }

        tryToLocalizeExceptionMessage(exception, errorInfo);

        if (StringUtils.isBlank(errorInfo.getMessage())) {
            errorInfo.setMessage(messageSourceAccessor.getMessage("InternalServerErrorMessage"));
        }

        if (exception instanceof IHasData) {
            errorInfo.setData(((IHasData) exception).getData());
        }
        return errorInfo;
    }

    protected void tryToLocalizeExceptionMessage(Exception exception, RemoteServiceErrorInfo errorInfo) {
        if (exception instanceof ILocalizeErrorMessage) {
            errorInfo.setMessage(((ILocalizeErrorMessage) exception).localizeMessage(currentLocale.getLocale()));
        }
        if (!(exception instanceof IHasErrorCode)) {
            return;
        }
        String errorCode = ((IHasErrorCode) exception).getCode();
        if (StringUtils.isBlank(errorCode) || !errorCode.contains(":")) {
            return;
        }

        String localizedValue = messageSourceAccessor.getMessage(errorCode);

        if (exception instanceof IHasData) {
            if (((IHasData) exception).getData() != null && ((IHasData) exception).getData().size() > 0) {
                for (String key : ((IHasData) exception).getData().keySet()) {
                    localizedValue = localizedValue.replace("{" + key + "}", Optional.ofNullable(((IHasData) exception).getData().get(key))
                            .map(Object::toString).orElse(""));
                }
            }
        }

        errorInfo.setMessage(localizedValue);
    }

    protected RemoteServiceErrorInfo createEntityNotFoundError(EntityNotFoundException exception) {
        if (exception.getEntityType() != null) {
            return new RemoteServiceErrorInfo(
                    messageSourceAccessor.getMessage("EntityNotFoundErrorMessage", new String[]{exception.getEntityType().getName(), exception.getId().toString()}),
                    null, null
            );
        }

        return new RemoteServiceErrorInfo(exception.getMessage(), null, null);
    }

    protected Exception tryToGetActualException(Exception exception) {
        if (exception.getCause() != null) {
            Throwable innerException = exception.getCause();
            if (innerException instanceof ValidationException
                    || innerException instanceof AuthorizationException
                    || innerException instanceof EntityNotFoundException
                    || innerException instanceof BusinessException) {
                return (Exception) innerException;
            }
        }
        return exception;
    }

    protected RemoteServiceErrorInfo createDetailedErrorInfoFromException(Exception exception, boolean sendStackTraceToClients) {
        StringBuilder detailBuilder = new StringBuilder();

        addExceptionToDetails(exception, detailBuilder, sendStackTraceToClients);

        RemoteServiceErrorInfo errorInfo = new RemoteServiceErrorInfo(exception.getMessage(), detailBuilder.toString(), null);

        if (exception instanceof ValidationException) {
            errorInfo.setValidationErrors(getValidationErrorInfos((ValidationException) exception));
        }

        return errorInfo;
    }

    protected void addExceptionToDetails(Throwable exception, StringBuilder detailBuilder, boolean sendStackTraceToClients) {
        //Exception Message
        detailBuilder.append(exception.getClass().getName() + ": " + exception.getMessage()).append(StringUtils.CR);

        //Additional info for UserFriendlyException
        if (exception instanceof IUserFriendlyException && exception instanceof IHasErrorDetails) {
            String details = ((IHasErrorDetails) exception).getDetails();
            if (StringUtils.isNotBlank(details)) {
                detailBuilder.append(details).append(StringUtils.CR);
            }
        }

        //Additional info for AbpValidationException
        if (exception instanceof ValidationException) {
            ValidationException validationException = (ValidationException) exception;
            if (validationException.getValidationErrors().size() > 0) {
                detailBuilder.append(getValidationErrorNarrative(validationException)).append(StringUtils.CR);
            }
        }

        //Exception StackTrace
        if (sendStackTraceToClients && ArrayUtils.isNotEmpty(exception.getStackTrace())) {
            detailBuilder.append("STACK TRACE: ").append(Arrays.toString(exception.getStackTrace())).append(StringUtils.CR);
        }

        //Inner exception
        if (exception.getCause() != null) {
            addExceptionToDetails(exception.getCause(), detailBuilder, sendStackTraceToClients);
        }
    }

    protected List<RemoteServiceValidationErrorInfo> getValidationErrorInfos(IHasValidationErrors validationException) {
        List<RemoteServiceValidationErrorInfo> validationErrorInfos = new ArrayList<>();

        for (ValidationResult validationResult : validationException.getValidationErrors()) {
            RemoteServiceValidationErrorInfo validationError = new RemoteServiceValidationErrorInfo(validationResult.getErrorMessage().toString());

            if (validationResult.getMemberNames() != null && !validationResult.getMemberNames().isEmpty()) {
                validationError.setMembers(validationResult.getMemberNames().stream().map(CharSequence::toString).collect(Collectors.toList()));
            }

            validationErrorInfos.add(validationError);
        }

        return validationErrorInfos;
    }

    protected String getValidationErrorNarrative(IHasValidationErrors validationException) {
        StringBuilder detailBuilder = new StringBuilder();
        detailBuilder.append(messageSourceAccessor.getMessage("ValidationNarrativeErrorMessageTitle")).append(StringUtils.CR);

        for (ValidationResult validationResult : validationException.getValidationErrors()) {
            detailBuilder.append(" - ").append(validationResult.getErrorMessage());
            detailBuilder.append(StringUtils.CR);
        }

        return detailBuilder.toString();
    }

    protected AbpExceptionHandlingOptions createDefaultOptions() {
        return new AbpExceptionHandlingOptions() {{
            setSendExceptionsDetailsToClients(false);
            setSendStackTraceToClients(true);
        }};
    }
}
