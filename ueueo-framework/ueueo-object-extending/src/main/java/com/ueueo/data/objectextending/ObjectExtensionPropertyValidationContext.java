package com.ueueo.data.objectextending;

import com.ueueo.data.annotations.ValidationContext;
import com.ueueo.data.annotations.ValidationResult;
import lombok.Data;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

@Data
public class ObjectExtensionPropertyValidationContext {
    /**
     * Related property extension information.
     */
    @NonNull
    private ObjectExtensionPropertyInfo extensionPropertyInfo;

    /**
     * Reference to the validating object.
     */
    @NonNull
    private IHasExtraProperties validatingObject;

    /**
     * Add validation errors to this list.
     */
    @NonNull
    private List<ValidationResult> validationResults;

    /**
     * Validation context comes from the <see cref="IValidatableObject.Validate"/> method.
     */
    @NonNull
    private ValidationContext validationContext;

    /**
     * The value of the validating property of the <see cref="ValidatingObject"/>.
     */
    @Nullable
    private Object value;

    /**
     * Can be used to resolve services from the dependency injection container.
     * This can be null when SetProperty method is used on the object.
     */
    @Nullable
    private BeanFactory beanFactory;

    public ObjectExtensionPropertyValidationContext(@NonNull ObjectExtensionPropertyInfo objectExtensionPropertyInfo,
                                                    @NonNull IHasExtraProperties validatingObject,
                                                    @NonNull List<ValidationResult> validationErrors,
                                                    @NonNull ValidationContext validationContext,
                                                    @Nullable Object value) {
        this.extensionPropertyInfo = objectExtensionPropertyInfo;
        this.validatingObject = validatingObject;
        this.validationResults = validationErrors;
        this.validationContext = validationContext;
        this.value = value;
    }
}
