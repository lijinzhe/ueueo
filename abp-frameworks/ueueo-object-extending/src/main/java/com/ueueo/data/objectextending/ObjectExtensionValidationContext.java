package com.ueueo.data.objectextending;

import com.ueueo.validation.ValidationContext;
import com.ueueo.validation.ValidationResult;
import lombok.Data;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

@Data
public class ObjectExtensionValidationContext {
    /**
     * Related object extension information.
     */
    @NonNull
    private ObjectExtensionInfo objectExtensionInfo;

    /**
     * Reference to the validating object.
     */
    @NonNull
    private IHasExtraProperties validatingObject;

    /**
     * Add validation errors to this list.
     */
    @NonNull
    private List<ValidationResult> validationErrors;

    /**
     * Validation context comes from the <see cref="IValidatableObject.Validate"/> method.
     */
    @NonNull
    private ValidationContext validationContext;

    /**
     * Can be used to resolve services from the dependency injection container.
     */
    @Nullable
    private BeanFactory beanFactory;

    public ObjectExtensionValidationContext(
            @NonNull ObjectExtensionInfo objectExtensionInfo,
            @NonNull IHasExtraProperties validatingObject,
            @NonNull List<ValidationResult> validationErrors,
            @NonNull ValidationContext validationContext) {
        this.objectExtensionInfo = objectExtensionInfo;
        this.validatingObject = validatingObject;
        this.validationErrors = validationErrors;
        this.validationContext = validationContext;
    }

}
