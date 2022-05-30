using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;

namespace Volo.Abp.Reflection;

//TODO: Consider to make internal
public static class ReflectionHelper
{
    //TODO: Ehhance summary
    /**
     * Checks whether <paramref name="givenType"/> implements/inherits <paramref name="genericType"/>.
    *
     * <param name="givenType">Type to check</param>
     * <param name="genericType">Generic type</param>
     */
    public static boolean IsAssignableToGenericType(Type givenType, Type genericType)
    {
        var givenTypeInfo = givenType.GetTypeInfo();

        if (givenTypeInfo.IsGenericType && givenType.GetGenericTypeDefinition() == genericType)
        {
            return true;
        }

        for (var interfaceType in givenTypeInfo.GetInterfaces())
        {
            if (interfaceType.GetTypeInfo().IsGenericType && interfaceType.GetGenericTypeDefinition() == genericType)
            {
                return true;
            }
        }

        if (givenTypeInfo.BaseType == null)
        {
            return false;
        }

        return IsAssignableToGenericType(givenTypeInfo.BaseType, genericType);
    }

    //TODO: Summary
    public static List<Type> GetImplementedGenericTypes(Type givenType, Type genericType)
    {
        var result = new List<Type>();
        AddImplementedGenericTypes(result, givenType, genericType);
        return result;
    }

    private static void AddImplementedGenericTypes(List<Type> result, Type givenType, Type genericType)
    {
        var givenTypeInfo = givenType.GetTypeInfo();

        if (givenTypeInfo.IsGenericType && givenType.GetGenericTypeDefinition() == genericType)
        {
            result.AddIfNotContains(givenType);
        }

        for (var interfaceType in givenTypeInfo.GetInterfaces())
        {
            if (interfaceType.GetTypeInfo().IsGenericType && interfaceType.GetGenericTypeDefinition() == genericType)
            {
                result.AddIfNotContains(interfaceType);
            }
        }

        if (givenTypeInfo.BaseType == null)
        {
            return;
        }

        AddImplementedGenericTypes(result, givenTypeInfo.BaseType, genericType);
    }

    /**
     * Tries to gets an of attribute defined for a class member and it's declaring type including inherited attributes.
     * Returns default value if it's not declared at all.
    *
     * <typeparam name="TAttribute">Type of the attribute</typeparam>
     * <param name="memberInfo">MemberInfo</param>
     * <param name="defaultValue">Default value (null as default)</param>
     * <param name="inherit">Inherit attribute from base classes</param>
     */
    public static TAttribute GetSingleAttributeOrDefault<TAttribute>(MemberInfo memberInfo, TAttribute defaultValue = default, boolean inherit = true)
        //where TAttribute : Attribute
    {
        //Get attribute on the member
        if (memberInfo.IsDefined(typeof(TAttribute), inherit))
        {
            return memberInfo.GetCustomAttributes(typeof(TAttribute), inherit).Cast<TAttribute>().First();
        }

        return defaultValue;
    }

    /**
     * Tries to gets an of attribute defined for a class member and it's declaring type including inherited attributes.
     * Returns default value if it's not declared at all.
    *
     * <typeparam name="TAttribute">Type of the attribute</typeparam>
     * <param name="memberInfo">MemberInfo</param>
     * <param name="defaultValue">Default value (null as default)</param>
     * <param name="inherit">Inherit attribute from base classes</param>
     */
    public static TAttribute GetSingleAttributeOfMemberOrDeclaringTypeOrDefault<TAttribute>(MemberInfo memberInfo, TAttribute defaultValue = default, boolean inherit = true)
        //where TAttribute : class
    {
        return memberInfo.GetCustomAttributes(true).OfType<TAttribute>().FirstOrDefault()
               ?? memberInfo.DeclaringType?.GetTypeInfo().GetCustomAttributes(true).OfType<TAttribute>().FirstOrDefault()
               ?? defaultValue;
    }

    /**
     * Tries to gets attributes defined for a class member and it's declaring type including inherited attributes.
    *
     * <typeparam name="TAttribute">Type of the attribute</typeparam>
     * <param name="memberInfo">MemberInfo</param>
     * <param name="inherit">Inherit attribute from base classes</param>
     */
    public static IEnumerable<TAttribute> GetAttributesOfMemberOrDeclaringType<TAttribute>(MemberInfo memberInfo, boolean inherit = true)
        //where TAttribute : class
    {
        var customAttributes = memberInfo.GetCustomAttributes(true).OfType<TAttribute>();
        var declaringTypeCustomAttributes =
            memberInfo.DeclaringType?.GetTypeInfo().GetCustomAttributes(true).OfType<TAttribute>();
        return declaringTypeCustomAttributes != null
            ? customAttributes.Concat(declaringTypeCustomAttributes).Distinct()
            : customAttributes;
    }

    /**
     * Gets value of a property by it's full path from given object
    */
    public static Object GetValueByPath(Object obj, Type objectType, String propertyPath)
    {
        var value = obj;
        var currentType = objectType;
        var objectPath = currentType.FullName;
        var absolutePropertyPath = propertyPath;
        if (objectPath != null && absolutePropertyPath.StartsWith(objectPath))
        {
            absolutePropertyPath = absolutePropertyPath.Replace(objectPath + ".", "");
        }

        for (var propertyName in absolutePropertyPath.Split('.'))
        {
            var property = currentType.GetProperty(propertyName);
            if (property != null)
            {
                if (value != null)
                {
                    value = property.GetValue(value, null);
                }
                currentType = property.PropertyType;
            }
            else
            {
                value = null;
                break;
            }
        }

        return value;
    }

    /**
     * Sets value of a property by it's full path on given object
    */
    internal static void SetValueByPath(Object obj, Type objectType, String propertyPath, Object value)
    {
        var currentType = objectType;
        PropertyInfo property;
        var objectPath = currentType.FullName;
        var absolutePropertyPath = propertyPath;
        if (absolutePropertyPath.StartsWith(objectPath))
        {
            absolutePropertyPath = absolutePropertyPath.Replace(objectPath + ".", "");
        }

        var properties = absolutePropertyPath.Split('.');

        if (properties.Length == 1)
        {
            property = objectType.GetProperty(properties.First());
            property.SetValue(obj, value);
            return;
        }

        for (int i = 0; i < properties.Length - 1; i++)
        {
            property = currentType.GetProperty(properties[i]);
            obj = property.GetValue(obj, null);
            currentType = property.PropertyType;
        }

        property = currentType.GetProperty(properties.Last());
        property.SetValue(obj, value);
    }


    /**
     * Get all the constant values in the specified type (including the base type).
     *
     * <param name="type"></param>
     * <returns></returns>
     */
    public static String[] GetPublicConstantsRecursively(Type type)
    {
        const int maxRecursiveParameterValidationDepth = 8;

        var publicConstants = new List<String>();

        void Recursively(List<String> constants, Type targetType, int currentDepth)
        {
            if (currentDepth > maxRecursiveParameterValidationDepth)
            {
                return;
            }

            constants.AddRange(targetType.GetFields(BindingFlags.Public | BindingFlags.Static | BindingFlags.FlattenHierarchy)
                .Where(x => x.IsLiteral && !x.IsInitOnly)
                .Select(x => x.GetValue(null).ToString()));

            var nestedTypes = targetType.GetNestedTypes(BindingFlags.Public);

            for (var nestedType in nestedTypes)
            {
                Recursively(constants, nestedType, currentDepth + 1);
            }
        }

        Recursively(publicConstants, type, 1);

        return publicConstants.ToArray();
    }
}
