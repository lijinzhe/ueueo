package com.ueueo.validation;

import com.ueueo.validation.annotations.Display;
import com.ueueo.validation.annotations.ValidationAttribute;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-25 15:49
 */
class ValidationAttributeStore {
    final static ValidationAttributeStore Instance = new ValidationAttributeStore();

    private ConcurrentHashMap<Class<?>, TypeStoreItem> typeStoreItems = new ConcurrentHashMap<>();

    /**
     * Retrieves the <see cref="DisplayAttribute" /> associated with the given type.  It may be null.
     *
     * @param validationContext The context that describes the type.  It cannot be null.
     *
     * @return The display attribute instance, if present.
     */
    Display getTypeDisplayAttribute(ValidationContext validationContext) {
        ensureValidationContext(validationContext);
        TypeStoreItem item = getTypeStoreItem(validationContext.getObjectType());
        return item.getDisplayAttribute();
    }

    /**
     * Retrieves the <see cref="DisplayAttribute" /> associated with the given property
     *
     * @param validationContext The context that describes the property.  It cannot be null.
     *
     * @return The display attribute instance, if present.
     */
    Display getPropertyDisplayAttribute(ValidationContext validationContext) {
        ensureValidationContext(validationContext);
        TypeStoreItem typeItem = getTypeStoreItem(validationContext.getObjectType());
        PropertyStoreItem item = typeItem.getPropertyStoreItem(validationContext.getMemberName());
        return item.getDisplayAttribute();
    }

    /**
     * Determines whether or not a given <see cref="ValidationContext" />'s
     *  *     <see cref="ValidationContext.MemberName" /> references a property on
     *  *     the <see cref="ValidationContext.ObjectType" />.
     *
     * @param validationContext The <see cref="ValidationContext" /> to check.
     *
     * @return <c>true</c> when the <paramref name="validationContext" /> represents a property, <c>false</c> otherwise.
     */
    boolean isPropertyContext(ValidationContext validationContext) {
        ensureValidationContext(validationContext);
        TypeStoreItem typeItem = getTypeStoreItem(validationContext.getObjectType());
        return typeItem.tryGetPropertyStoreItem(validationContext.getMemberName()) != null;
    }

    /**
     * Throws an ArgumentException of the validation context is null
     *
     * @param validationContext The context to check
     */
    private static void ensureValidationContext(ValidationContext validationContext) {
        if (validationContext == null) {
            throw new IllegalArgumentException("validationContext");
        }
    }

    /**
     * Retrieves or creates the store item for the given type
     *
     * @param type The type whose store item is needed.  It cannot be null
     *
     * @return The type store item.  It will not be null.
     */
    private TypeStoreItem getTypeStoreItem(Class<?> type) {
        return typeStoreItems.computeIfAbsent(type, t -> {
            TypeStoreItem item = new TypeStoreItem(t, t.getAnnotations());
            typeStoreItems.put(t, item);
            return item;
        });
    }

    /**
     * Private abstract class for all store items
     */
    private abstract class StoreItem {

        List<ValidationAttribute> validationAttributes;

        Display displayAttribute;

        StoreItem(Annotation[] attributes) {
            validationAttributes = Arrays.stream(attributes).filter(a -> a.annotationType().equals(ValidationAttribute.class))
                    .map(annotation -> (ValidationAttribute) annotation)
                    .collect(Collectors.toList());
            displayAttribute = Arrays.stream(attributes).filter(a -> a.annotationType().equals(Display.class))
                    .map(annotation -> (Display) annotation)
                    .findFirst().orElse(null);
        }

        public List<ValidationAttribute> getValidationAttributes() {
            return validationAttributes;
        }

        public Display getDisplayAttribute() {
            return displayAttribute;
        }
    }

    /**
     * Private class to store data associated with a type
     */
    private class TypeStoreItem extends StoreItem {
        private final Object syncRoot = new Object();
        private Class<?> type;
        private Map<String, PropertyStoreItem> propertyStoreItemMap;

        TypeStoreItem(Class<?> type, Annotation[] attributes) {
            super(attributes);
            this.type = type;
        }

        PropertyStoreItem getPropertyStoreItem(String propertyName) {
            PropertyStoreItem item = tryGetPropertyStoreItem(propertyName);
            if (item == null) {
                throw new IllegalArgumentException("propertyName");
            }

            return item;
        }

        PropertyStoreItem tryGetPropertyStoreItem(String propertyName) {
            if (StringUtils.isBlank(propertyName)) {
                throw new IllegalArgumentException("propertyName");
            }
            if (propertyStoreItemMap == null) {
                synchronized (syncRoot) {
                    if (propertyStoreItemMap == null) {
                        propertyStoreItemMap = createPropertyStoreItems();
                    }
                }
            }
            return propertyStoreItemMap.get(propertyName);
        }

        private Map<String, PropertyStoreItem> createPropertyStoreItems() {
            Map<String, PropertyStoreItem> propertyStoreItems = new HashMap<>();
            Field[] fields = type.getFields();
            for (Field field : fields) {
                PropertyStoreItem item = new PropertyStoreItem(field.getType(), field.getAnnotations());
                propertyStoreItems.put(field.getName(), item);
            }
            return propertyStoreItems;
        }

    }

    /**
     * Private class to store data associated with a property
     */
    private class PropertyStoreItem extends StoreItem {
        Class<?> propertyType;

        PropertyStoreItem(Class<?> propertyType, Annotation[] attributes) {
            super(attributes);
            this.propertyType = propertyType;
        }

        public Class<?> getPropertyType() {
            return propertyType;
        }
    }

}
