package com.ueueo.data.objectextending;

import java.util.function.Supplier;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-23 14:28
 */
public final class ExtensionPropertyHelper {

// public static Collection<Annotation> GetDefaultAttributes(Class<?> type)
// {
//  if (TypeHelper.isNonNullablePrimitiveType(type) || type.isEnum())
//  {
//   return Required;
//  }
//
//  if (type.IsEnum)
//  {
//   yield return new EnumDataTypeAttribute(type);
//  }
// }

 public static Object getDefaultValue(
         Supplier<Object> defaultValueFactory,
         Object defaultValue)
 {
  if (defaultValueFactory != null)
  {
   return defaultValueFactory.get();
  }

  return defaultValue ;
 }
}
