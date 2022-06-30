package com.ueueo.data.objectextending;

import com.alibaba.fastjson.util.TypeUtils;
import com.ueueo.SystemException;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Lee
 * @date 2022-05-23 13:39
 */
public interface IHasExtraProperties {

    ExtraPropertyDictionary getExtraProperties();

    /**
     * 扩展方法
     */
    class Extensions {

        public static boolean hasProperty(IHasExtraProperties source, String name) {
            return source.getExtraProperties().containsKey(name);
        }

        public static Object getProperty(IHasExtraProperties source, String name, Object defaultValue) {
            return source.getExtraProperties().getOrDefault(name, defaultValue);
        }

        public static <TProperty> TProperty getProperty(IHasExtraProperties source, String name, Class<TProperty> propertyType, TProperty defaultValue) {
            Object value = getProperty(source, name, null);
            if (value == null) {
                return defaultValue;
            } else {
                try {
                    return TypeUtils.castToJavaBean(value, propertyType);
                } catch (Exception e) {
                    throw new SystemException("GetProperty<TProperty> does not support non-primitive types. Use non-generic GetProperty method and handle type casting manually.");
                }
            }
        }

        public static <TSource extends IHasExtraProperties> TSource setProperty(TSource source, String name,
                                                                                Object value, Boolean validate) {
            if (validate == null || validate) {
                ExtensibleObjectValidator.checkValue(source, name, value);
            }

            source.getExtraProperties().put(name, value);
            return source;
        }

        public static <TSource extends IHasExtraProperties> TSource removeProperty(TSource source, String name) {
            source.getExtraProperties().remove(name);
            return source;
        }

        public static <TSource extends IHasExtraProperties> TSource setDefaultsForExtraProperties(TSource source, Class<?> objectType) {
            if (objectType == null) {
                objectType = source.getClass();
            }

            Collection<ObjectExtensionPropertyInfo> properties = ObjectExtensionManager.Instance
                    .getProperties(objectType);

            for (ObjectExtensionPropertyInfo property : properties) {
                if (hasProperty(source, property.getName())) {
                    continue;
                }
                source.getExtraProperties().put(property.getName(), property.getDefaultValue());
            }

            return source;
        }

        public static void setDefaultsForExtraProperties(Object source, Class<?> objectType) {
            if (!(source instanceof IHasExtraProperties)) {
                throw new IllegalArgumentException("Given source object does not implement the IHasExtraProperties interface!");
            }
            setDefaultsForExtraProperties((IHasExtraProperties) source, objectType);
        }

        public static void setExtraPropertiesToRegularProperties(IHasExtraProperties source) {
            Arrays.stream(source.getClass().getFields()).filter(field -> source.getExtraProperties().containsKey(field.getName()))
                    .forEach(field -> {
                        try {
                            FieldUtils.writeField(field, source, source.getExtraProperties().get(field.getName()));
                            source.getExtraProperties().remove(field.getName());
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });

        }

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
        public void mapExtraPropertiesTo(
                IHasExtraProperties source,
                IHasExtraProperties destination,
                MappingPropertyDefinitionChecksEnum definitionChecks,
                Collection<String> ignoredProperties) {
            ExtensibleObjectMapper.mapExtraPropertiesTo(
                    source,
                    destination,
                    definitionChecks,
                    ignoredProperties
            );
        }
    }
}
