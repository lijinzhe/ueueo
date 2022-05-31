package com.ueueo.reflection;

import java.util.Arrays;

public class TypeHelper {
    private static Class<?>[] FloatingTypes = new Class[]
            {
                    double.class,
                    float.class
                    //            typeof(decimal)
            };

    private static Class<?>[] NonNullablePrimitiveTypes = new Class[]
            {
                    byte.class,
                    short.class,
                    int.class,
                    long.class,
                    double.class,
                    float.class,
                    boolean.class
            };
    //        {
    //            typeof(byte),
    //            typeof(short),
    //            typeof(int),
    //            typeof(long),
    //            typeof(sbyte),
    //            typeof(ushort),
    //            typeof(uint),
    //            typeof(ulong),
    //            typeof(bool),
    //            typeof(float),
    //            typeof(decimal),
    //            typeof(DateTime),
    //            typeof(DateTimeOffset),
    //            typeof(TimeSpan),
    //            typeof(Guid)
    //        };

    public static boolean isNonNullablePrimitiveType(Class<?> type) {
        return Arrays.asList(NonNullablePrimitiveTypes).contains(type);
    }

    //    public static boolean IsFunc(Object obj)
    //    {
    //        if (obj == null)
    //        {
    //            return false;
    //        }
    //
    //        var type = obj.GetType();
    //        if (!type.GetTypeInfo().IsGenericType)
    //        {
    //            return false;
    //        }
    //
    //        return type.GetGenericTypeDefinition() == typeof(Func<>);
    //    }
    //
    //    public static boolean IsFunc<TReturn>(Object obj)
    //    {
    //        return obj != null && obj.GetType() == typeof(Func<TReturn>);
    //    }

    //    public static boolean IsPrimitiveExtended(Type type, boolean includeNullables = true, boolean includeEnums = false)
    //    {
    //        if (IsPrimitiveExtendedInternal(type, includeEnums))
    //        {
    //            return true;
    //        }
    //
    //        if (includeNullables && IsNullable(type) && type.GenericTypeArguments.Any())
    //        {
    //            return IsPrimitiveExtendedInternal(type.GenericTypeArguments[0], includeEnums);
    //        }
    //
    //        return false;
    //    }
    //
    //    public static boolean IsNullable(Type type)
    //    {
    //        return type.IsGenericType && type.GetGenericTypeDefinition() == typeof(Nullable<>);
    //    }
    //
    //    public static Type GetFirstGenericArgumentIfNullable(this Type t)
    //    {
    //        if (t.GetGenericArguments().Length > 0 && t.GetGenericTypeDefinition() == typeof(Nullable<>))
    //        {
    //            return t.GetGenericArguments().FirstOrDefault();
    //        }
    //
    //        return t;
    //    }
    //
    //    public static boolean IsEnumerable(Type type, out Type itemType, boolean includePrimitives = true)
    //    {
    //        if (!includePrimitives && IsPrimitiveExtended(type))
    //        {
    //            itemType = null;
    //            return false;
    //        }
    //
    //        var enumerableTypes = ReflectionHelper.GetImplementedGenericTypes(type, typeof(IEnumerable<>));
    //        if (enumerableTypes.Count == 1)
    //        {
    //            itemType = enumerableTypes[0].GenericTypeArguments[0];
    //            return true;
    //        }
    //
    //        if (typeof(IEnumerable).IsAssignableFrom(type))
    //        {
    //            itemType = typeof(object);
    //            return true;
    //        }
    //
    //        itemType = null;
    //        return false;
    //    }
    //
    //    public static boolean IsDictionary(Type type, out Type keyType, out Type valueType)
    //    {
    //        var dictionaryTypes = ReflectionHelper
    //            .GetImplementedGenericTypes(
    //                type,
    //                typeof(IDictionary<,>)
    //            );
    //
    //        if (dictionaryTypes.Count == 1)
    //        {
    //            keyType = dictionaryTypes[0].GenericTypeArguments[0];
    //            valueType = dictionaryTypes[0].GenericTypeArguments[1];
    //            return true;
    //        }
    //
    //        if (typeof(IDictionary).IsAssignableFrom(type))
    //        {
    //            keyType = typeof(object);
    //            valueType = typeof(object);
    //            return true;
    //        }
    //
    //        keyType = null;
    //        valueType = null;
    //
    //        return false;
    //    }
    //
    //    private static boolean IsPrimitiveExtendedInternal(Type type, boolean includeEnums)
    //    {
    //        if (type.IsPrimitive)
    //        {
    //            return true;
    //        }
    //
    //        if (includeEnums && type.IsEnum)
    //        {
    //            return true;
    //        }
    //
    //        return type == typeof(String) ||
    //               type == typeof(decimal) ||
    //               type == typeof(DateTime) ||
    //               type == typeof(DateTimeOffset) ||
    //               type == typeof(TimeSpan) ||
    //               type == typeof(Guid);
    //    }

    //    public static T GetDefaultValue<T>()
    //    {
    //        return default;
    //    }

    //    public static Object GetDefaultValue(Class<?> type)
    //    {
    //        if (type.IsValueType)
    //        {
    //            return Activator.CreateInstance(type);
    //        }
    //
    //        return null;
    //    }
    //
    //    public static String GetFullNameHandlingNullableAndGenerics(@Nonnull Type type)
    //    {
    //        Objects.requireNonNull(type, nameof(type));
    //
    //        if (type.IsGenericType && type.GetGenericTypeDefinition() == typeof(Nullable<>))
    //        {
    //            return type.GenericTypeArguments[0].FullName + "?";
    //        }
    //
    //        if (type.IsGenericType)
    //        {
    //            var genericType = type.GetGenericTypeDefinition();
    //            return $"{genericType.FullName.Left(genericType.FullName.IndexOf('`'))}<{type.GenericTypeArguments.Select(GetFullNameHandlingNullableAndGenerics).JoinAsString(",")}>";
    //        }
    //
    //        return type.FullName ?? type.Name;
    //    }
    //
    //    public static String GetSimplifiedName(@Nonnull Type type)
    //    {
    //        Objects.requireNonNull(type, nameof(type));
    //
    //        if (type.IsGenericType && type.GetGenericTypeDefinition() == typeof(Nullable<>))
    //        {
    //            return GetSimplifiedName(type.GenericTypeArguments[0]) + "?";
    //        }
    //
    //        if (type.IsGenericType)
    //        {
    //            var genericType = type.GetGenericTypeDefinition();
    //            return $"{genericType.FullName.Left(genericType.FullName.IndexOf('`'))}<{type.GenericTypeArguments.Select(GetSimplifiedName).JoinAsString(",")}>";
    //        }
    //
    //        if (type == typeof(String))
    //        {
    //            return "string";
    //        }
    //        else if (type == typeof(int))
    //        {
    //            return "number";
    //        }
    //        else if (type == typeof(long))
    //        {
    //            return "number";
    //        }
    //        else if (type == typeof(bool))
    //        {
    //            return "boolean";
    //        }
    //        else if (type == typeof(char))
    //        {
    //            return "string";
    //        }
    //        else if (type == typeof(double))
    //        {
    //            return "number";
    //        }
    //        else if (type == typeof(float))
    //        {
    //            return "number";
    //        }
    //        else if (type == typeof(decimal))
    //        {
    //            return "number";
    //        }
    //        else if (type == typeof(DateTime))
    //        {
    //            return "string";
    //        }
    //        else if (type == typeof(DateTimeOffset))
    //        {
    //            return "string";
    //        }
    //        else if (type == typeof(TimeSpan))
    //        {
    //            return "string";
    //        }
    //        else if (type == typeof(Guid))
    //        {
    //            return "string";
    //        }
    //        else if (type == typeof(byte))
    //        {
    //            return "number";
    //        }
    //        else if (type == typeof(sbyte))
    //        {
    //            return "number";
    //        }
    //        else if (type == typeof(short))
    //        {
    //            return "number";
    //        }
    //        else if (type == typeof(ushort))
    //        {
    //            return "number";
    //        }
    //        else if (type == typeof(uint))
    //        {
    //            return "number";
    //        }
    //        else if (type == typeof(ulong))
    //        {
    //            return "number";
    //        }
    //        else if (type == typeof(IntPtr))
    //        {
    //            return "number";
    //        }
    //        else if (type == typeof(UIntPtr))
    //        {
    //            return "number";
    //        }
    //        else if (type == typeof(object))
    //        {
    //            return "object";
    //        }
    //
    //        return type.FullName ?? type.Name;
    //    }
    //
    //    public static Object ConvertFromString<TTargetType>(String value)
    //    {
    //        return ConvertFromString(typeof(TTargetType), value);
    //    }
    //
    //    public static Object ConvertFromString(Type targetType, String value)
    //    {
    //        if (value == null)
    //        {
    //            return null;
    //        }
    //
    //        var converter = TypeDescriptor.GetConverter(targetType);
    //
    //        if (IsFloatingType(targetType))
    //        {
    //            using (CultureHelper.Use(CultureInfo.InvariantCulture))
    //            {
    //                return converter.ConvertFromString(value.Replace(',', '.'));
    //            }
    //        }
    //
    //        return converter.ConvertFromString(value);
    //    }
    //
    //    public static boolean IsFloatingType(Type type, boolean includeNullable = true)
    //    {
    //        if (FloatingTypes.Contains(type))
    //        {
    //            return true;
    //        }
    //
    //        if (includeNullable &&
    //            IsNullable(type) &&
    //            FloatingTypes.Contains(type.GenericTypeArguments[0]))
    //        {
    //            return true;
    //        }
    //
    //        return false;
    //    }
    //
    //    public static Object ConvertFrom<TTargetType>(Object value)
    //    {
    //        return ConvertFrom(typeof(TTargetType), value);
    //    }
    //
    //    public static Object ConvertFrom(Type targetType, Object value)
    //    {
    //        return TypeDescriptor
    //            .GetConverter(targetType)
    //            .ConvertFrom(value);
    //    }
    //
    //    public static Type StripNullable(Type type)
    //    {
    //        return IsNullable(type)
    //            ? type.GenericTypeArguments[0]
    //            : type;
    //    }
    //
    //    public static boolean IsDefaultValue(@Nullable Object obj)
    //    {
    //        if (obj == null)
    //        {
    //            return true;
    //        }
    //
    //        return obj.Equals(GetDefaultValue(obj.GetType()));
    //    }
}
