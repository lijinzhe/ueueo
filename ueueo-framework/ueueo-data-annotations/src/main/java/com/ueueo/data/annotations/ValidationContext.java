package com.ueueo.data.annotations;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-25 15:38
 */
@Getter
public class ValidationContext {
    /**
     * The dictionary of key/value pairs associated with this context.
     *
     * This property will never be null, but the dictionary may be empty.  Changes made
     * to items in this dictionary will never affect the original dictionary specified in the constructor.
     */
    private Map<Object, Object> items;
    /**
     * The user-visible name of the type or property being validated.
     *
     * If this name was not explicitly set, this property will consult an associated <see cref="DisplayAttribute" />
     * to see if can use that instead.  Lacking that, it returns <see cref="MemberName" />.  The
     * <see cref="ObjectInstance" />
     * type name will be used if MemberName has not been set.
     */
    private String displayName;
    /**
     * Gets the object instance being validated.  While it will not be null, the state of the instance is indeterminate
     * as it might only be partially initialized during validation.
     * <para>Consume this instance with caution!</para>
     *
     * During validation, especially property-level validation, the object instance might be in an indeterminate state.
     * For example, the property being validated, as well as other properties on the instance might not have been
     * updated to their new values.
     */
    @Getter
    private final Object objectInstance;

    /**
     * The name of the type or property being validated.
     *
     * This name reflects the API name of the member being validated, not a localized name.  It should be set
     * only for property or parameter contexts.
     */
    @Getter
    @Setter
    private String memberName;

    /**
     * Construct a <see cref="ValidationContext" /> for a given object instance being validated.
     *
     * @param instance The object instance being validated.  It cannot be <c>null</c>.
     */
    public ValidationContext(Object instance) {
        this(instance, null);
    }

    /**
     * Construct a <see cref="ValidationContext" /> for a given object instance, an optional
     * property bag of <paramref name="items" />.
     *
     * @param instance The object instance being validated.  It cannot be null.
     * @param items    Optional set of key/value pairs to make available to consumers via <see cref="Items" />.
     *                 if null, an empty dictionary will be created.  If not null, the set of key/value pairs will be copied into a
     *                 new dictionary, preventing consumers from modifying the original dictionary.
     */
    public ValidationContext(Object instance, Map<Object, Object> items) {
        Objects.requireNonNull(instance);
        this.objectInstance = instance;
        this.items = items != null ? new HashMap<>(items) : new HashMap<>();
    }

    /**
     * Gets the user-visible name of the type or property being validated.
     *
     * @return
     */
    public String getDisplayName() {
        if (StringUtils.isBlank(displayName)) {
            displayName = getDisplayNameFromDisplayAnnotation();
        }
        if (StringUtils.isBlank(displayName)) {
            displayName = getObjectType().getTypeName();
        }
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Class<?> getObjectType() {
        return objectInstance.getClass();
    }

    /**
     * Looks up the display name using the DisplayAttribute attached to the respective type or property.
     *
     * @return A display-friendly name of the member represented by the <see cref="MemberName" />.
     */
    private String getDisplayNameFromDisplayAnnotation() {
        String displayName = null;
        ValidationAttributeStore store = ValidationAttributeStore.Instance;
        Display displayAttribute = null;
        if (StringUtils.isBlank(memberName)) {
            displayAttribute = store.getTypeDisplayAttribute(this);
        } else if (store.isPropertyContext(this)) {
            displayAttribute = store.getPropertyDisplayAttribute(this);
        }
        if (displayAttribute != null) {
            displayName = displayAttribute.name();
        }
        return displayName != null ? displayName : memberName;
    }
}
