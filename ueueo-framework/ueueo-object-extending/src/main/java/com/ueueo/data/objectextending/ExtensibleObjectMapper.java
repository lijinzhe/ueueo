package com.ueueo.data.objectextending;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class ExtensibleObjectMapper {

    /**
     * Copies extra properties from the <paramref name="source"/> object
     * to the <paramref name="destination"/> object.
     *
     * Checks property definitions (over the <see cref="ObjectExtensionManager"/>)
     * based on the <paramref name="definitionChecks"/> preference.
     *
     * @param source            The source object
     * @param destination       The destination object
     * @param definitionChecks  Controls which properties to map.
     * @param ignoredProperties Used to ignore some properties
     */
    public static void mapExtraPropertiesTo(
            @NonNull IHasExtraProperties source,
            @NonNull IHasExtraProperties destination,
            MappingPropertyDefinitionChecksEnum definitionChecks,
            Collection<String> ignoredProperties) {
        Objects.requireNonNull(source);
        Objects.requireNonNull(destination);

        ExtensibleObjectMapper.mapExtraPropertiesTo(
                source.getClass(),
                destination.getClass(),
                source.getExtraProperties(),
                destination.getExtraProperties(),
                definitionChecks,
                ignoredProperties
        );
    }

    /**
     * Copies extra properties from the <paramref name="sourceDictionary"/> object
     * to the <paramref name="destinationDictionary"/> object.
     *
     * Checks property definitions (over the <see cref="ObjectExtensionManager"/>)
     * based on the <paramref name="definitionChecks"/> preference.
     *
     * @param sourceType            Source type (for definition check)
     * @param destinationType       Destination class type (for definition check)
     * @param sourceDictionary      The source dictionary object
     * @param destinationDictionary The destination dictionary object
     * @param definitionChecks      Controls which properties to map.
     * @param ignoredProperties     Used to ignore some properties
     */
    public static void mapExtraPropertiesTo(
            @NonNull Class<?> sourceType,
            @NonNull Class<?> destinationType,
            @NonNull Map<String, Object> sourceDictionary,
            @NonNull Map<String, Object> destinationDictionary,
            MappingPropertyDefinitionChecksEnum definitionChecks,
            Collection<String> ignoredProperties) {
        Objects.requireNonNull(sourceType);
        Objects.requireNonNull(destinationType);
        Objects.requireNonNull(sourceDictionary);
        Objects.requireNonNull(destinationDictionary);

        ObjectExtensionInfo sourceObjectExtension = ObjectExtensionManager.Instance.getOrNull(sourceType);
        ObjectExtensionInfo destinationObjectExtension = ObjectExtensionManager.Instance.getOrNull(destinationType);

        for (Map.Entry<String, Object> keyValue : sourceDictionary.entrySet()) {
            if (canMapProperty(
                    keyValue.getKey(),
                    sourceObjectExtension,
                    destinationObjectExtension,
                    definitionChecks,
                    ignoredProperties)) {
                destinationDictionary.put(keyValue.getKey(), keyValue.getValue());
            }
        }
    }

    public static boolean canMapProperty(
            @NonNull Class<?> sourceType,
            @NonNull Class<?> destinationType,
            @NonNull String propertyName,
            MappingPropertyDefinitionChecksEnum definitionChecks,
            Collection<String> ignoredProperties) {
        Objects.requireNonNull(sourceType);
        Objects.requireNonNull(destinationType);
        Objects.requireNonNull(propertyName);

        ObjectExtensionInfo sourceObjectExtension = ObjectExtensionManager.Instance.getOrNull(sourceType);
        ObjectExtensionInfo destinationObjectExtension = ObjectExtensionManager.Instance.getOrNull(destinationType);

        return canMapProperty(
                propertyName,
                sourceObjectExtension,
                destinationObjectExtension,
                definitionChecks,
                ignoredProperties);
    }

    private static boolean canMapProperty(
            @NonNull String propertyName,
            @Nullable ObjectExtensionInfo sourceObjectExtension,
            @Nullable ObjectExtensionInfo destinationObjectExtension,
            MappingPropertyDefinitionChecksEnum definitionChecks,
            Collection<String> ignoredProperties) {
        Objects.requireNonNull(propertyName);

        if (ignoredProperties != null &&
                ignoredProperties.contains(propertyName)) {
            return false;
        }

        if (definitionChecks != null) {
            if (definitionChecks.hasFlag(MappingPropertyDefinitionChecksEnum.Source)) {
                if (sourceObjectExtension == null) {
                    return false;
                }

                if (!sourceObjectExtension.hasProperty(propertyName)) {
                    return false;
                }
            }

            if (definitionChecks.hasFlag(MappingPropertyDefinitionChecksEnum.Destination)) {
                if (destinationObjectExtension == null) {
                    return false;
                }

                if (!destinationObjectExtension.hasProperty(propertyName)) {
                    return false;
                }
            }

            return true;
        } else {
            ObjectExtensionPropertyInfo sourcePropertyDefinition = sourceObjectExtension != null ?
                    sourceObjectExtension.getPropertyOrNull(propertyName) : null;
            ObjectExtensionPropertyInfo destinationPropertyDefinition = destinationObjectExtension != null ?
                    destinationObjectExtension.getPropertyOrNull(propertyName) : null;

            if (sourcePropertyDefinition != null) {
                if (destinationPropertyDefinition != null) {
                    return true;
                }

                if (!Boolean.TRUE.equals(sourcePropertyDefinition.getCheckPairDefinitionOnMapping())) {
                    return true;
                }
            } else if (destinationPropertyDefinition != null) {
                if (!Boolean.TRUE.equals(destinationPropertyDefinition.getCheckPairDefinitionOnMapping())) {
                    return true;
                }
            }

            return false;
        }
    }
}
