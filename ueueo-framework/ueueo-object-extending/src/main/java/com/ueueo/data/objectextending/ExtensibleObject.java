package com.ueueo.data.objectextending;

import com.ueueo.data.annotations.IValidatableObject;
import com.ueueo.data.annotations.ValidationContext;
import com.ueueo.data.annotations.ValidationResult;
import com.ueueo.dynamicproxy.ProxyHelper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

/**
 * @author Lee
 * @date 2022-05-23 13:39
 */
public class ExtensibleObject implements IHasExtraProperties, IValidatableObject {
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private ExtraPropertyDictionary extraProperties;

    public ExtensibleObject() {
        this(true);
    }

    public ExtensibleObject(boolean setDefaultsForExtraProperties) {
        extraProperties = new ExtraPropertyDictionary();

        if (setDefaultsForExtraProperties) {
            IHasExtraProperties.Extensions.setDefaultsForExtraProperties(this, ProxyHelper.unProxy(this).getClass());
        }
    }

    @Override
    public Collection<ValidationResult> validate(ValidationContext validationContext) {
        return ExtensibleObjectValidator.getValidationErrors(
                this,
                validationContext
        );
    }
}
