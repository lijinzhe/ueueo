package com.ueueo.data.objectextending;

import com.ueueo.data.annotations.ValidationAttribute;
import com.ueueo.data.annotations.ValidationContext;
import com.ueueo.data.annotations.ValidationResult;
import com.ueueo.dynamicproxy.ProxyHelper;
import com.ueueo.validation.AbpValidationException;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-31 16:07
 */
public class ExtensibleObjectValidator {

    public static void checkValue(@NonNull IHasExtraProperties extensibleObject,
                                  @NonNull String propertyName,
                                  @Nullable Object value) {
        List<ValidationResult> validationErrors = getValidationErrors(extensibleObject, propertyName, value, null);
        if (!validationErrors.isEmpty()) {
            throw new AbpValidationException(validationErrors);
        }
    }

    public static boolean isValid(@NonNull IHasExtraProperties extensibleObject,
                                  @Nullable ValidationContext objectValidationContext) {
        return CollectionUtils.isNotEmpty(getValidationErrors(extensibleObject, objectValidationContext));
    }

    public static boolean isValid(@NonNull IHasExtraProperties extensibleObject,
                                  @NonNull String propertyName,
                                  @Nullable Object value,
                                  @Nullable ValidationContext objectValidationContext) {
        return CollectionUtils.isNotEmpty(getValidationErrors(extensibleObject, propertyName, value, objectValidationContext));
    }

    @NonNull
    public static List<ValidationResult> getValidationErrors(@NonNull IHasExtraProperties extensibleObject,
                                                             @Nullable ValidationContext objectValidationContext) {
        List<ValidationResult> validationErrors = new ArrayList<>();

        addValidationErrors(extensibleObject, validationErrors, objectValidationContext);

        return validationErrors;
    }

    @NonNull
    public static List<ValidationResult> getValidationErrors(@NonNull IHasExtraProperties extensibleObject,
                                                             @NonNull String propertyName,
                                                             @Nullable Object value,
                                                             @Nullable ValidationContext objectValidationContext) {
        List<ValidationResult> validationErrors = new ArrayList<>();
        addValidationErrors(extensibleObject, validationErrors, propertyName, value, objectValidationContext);
        return validationErrors;
    }

    public static void addValidationErrors(@NonNull IHasExtraProperties extensibleObject,
                                           @NonNull List<ValidationResult> validationErrors,
                                           @Nullable ValidationContext objectValidationContext) {
        Objects.requireNonNull(extensibleObject);
        Objects.requireNonNull(validationErrors);

        if (objectValidationContext == null) {
            objectValidationContext = new ValidationContext(extensibleObject, new HashMap<>());
        }

        Class<?> objectType = ProxyHelper.unProxy(extensibleObject).getClass();

        ObjectExtensionInfo objectExtensionInfo = ObjectExtensionManager.Instance.getOrNull(objectType);

        if (objectExtensionInfo == null) {
            return;
        }

        addPropertyValidationErrors(extensibleObject, validationErrors, objectValidationContext, objectExtensionInfo);

        executeCustomObjectValidationActions(extensibleObject, validationErrors, objectValidationContext, objectExtensionInfo);
    }

    public static void addValidationErrors(@NonNull IHasExtraProperties extensibleObject,
                                           @NonNull List<ValidationResult> validationErrors,
                                           @NonNull String propertyName,
                                           @Nullable Object value,
                                           @Nullable ValidationContext objectValidationContext) {
        Objects.requireNonNull(extensibleObject);
        Objects.requireNonNull(validationErrors);
        Objects.requireNonNull(propertyName);

        if (objectValidationContext == null) {
            objectValidationContext = new ValidationContext(extensibleObject, new HashMap<>());
        }
        Class<?> objectType = ProxyHelper.unProxy(extensibleObject).getClass();
        ObjectExtensionInfo objectExtensionInfo = ObjectExtensionManager.Instance.getOrNull(objectType);

        if (objectExtensionInfo == null) {
            return;
        }
        ObjectExtensionPropertyInfo property = objectExtensionInfo.getPropertyOrNull(propertyName);
        if (property == null) {
            return;
        }
        addPropertyValidationErrors(extensibleObject, validationErrors, objectValidationContext, property, value);
    }

    private static void addPropertyValidationErrors(IHasExtraProperties extensibleObject,
                                                    List<ValidationResult> validationErrors,
                                                    ValidationContext objectValidationContext,
                                                    ObjectExtensionInfo objectExtensionInfo) {
        List<ObjectExtensionPropertyInfo> properties = objectExtensionInfo.getProperties();
        if (properties.isEmpty()) {
            return;
        }

        for (ObjectExtensionPropertyInfo property : properties) {
            addPropertyValidationErrors(extensibleObject, validationErrors, objectValidationContext, property, IHasExtraProperties.Extensions.getProperty(extensibleObject, property.getName(), null));
        }
    }

    private static void addPropertyValidationErrors(IHasExtraProperties extensibleObject,
                                                    List<ValidationResult> validationErrors,
                                                    ValidationContext objectValidationContext,
                                                    ObjectExtensionPropertyInfo property,
                                                    Object value) {
        addPropertyValidationAttributeErrors(extensibleObject, validationErrors, objectValidationContext, property, value);
        executeCustomPropertyValidationActions(extensibleObject, validationErrors, objectValidationContext, property, value);
    }

    private static void addPropertyValidationAttributeErrors(IHasExtraProperties extensibleObject,
                                                             List<ValidationResult> validationErrors,
                                                             ValidationContext objectValidationContext,
                                                             ObjectExtensionPropertyInfo property,
                                                             Object value) {
        List<ValidationAttribute> validationAttributes = property.getValidationAttributes();

        if (validationAttributes.isEmpty()) {
            return;
        }
        ValidationContext propertyValidationContext = new ValidationContext(extensibleObject, null);
        propertyValidationContext.setDisplayName(property.getName());
        propertyValidationContext.setMemberName(property.getName());

        for (ValidationAttribute attribute : validationAttributes) {
            //TODO 执行验证逻辑
//            Set<ConstraintViolation<Object>> result = propertyValidationContext.getValidator().validate(value);
//            ValidationResult result = attribute.GetValidationResult(value, propertyValidationContext);

//            if (result != null) {
//                validationErrors.add(result);
//            }
        }
    }

    private static void executeCustomPropertyValidationActions(IHasExtraProperties extensibleObject, List<ValidationResult> validationErrors, ValidationContext objectValidationContext, ObjectExtensionPropertyInfo property, Object value) {
        if (property.getValidators().isEmpty()) {
            return;
        }
        ObjectExtensionPropertyValidationContext context = new ObjectExtensionPropertyValidationContext(property,
                extensibleObject, validationErrors, objectValidationContext, value);

        for (Consumer<ObjectExtensionPropertyValidationContext> validator : property.getValidators()) {
            validator.accept(context);
        }
    }

    private static void executeCustomObjectValidationActions(IHasExtraProperties extensibleObject,
                                                             List<ValidationResult> validationErrors,
                                                             ValidationContext objectValidationContext,
                                                             ObjectExtensionInfo objectExtensionInfo)
    {
        if (objectExtensionInfo.getValidators().isEmpty()) {
            return;
        }
        ObjectExtensionValidationContext context = new ObjectExtensionValidationContext(objectExtensionInfo,
                extensibleObject, validationErrors, objectValidationContext);

        for (Consumer<ObjectExtensionValidationContext> validator : objectExtensionInfo.getValidators()) {
            validator.accept(context);
        }
    }
}
