package com.ueueo.data.objectextending;

import java.lang.reflect.Type;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-23 13:39
 */
public interface IHasExtraProperties {
    ExtraPropertyDictionary getExtraProperties();

 default    IHasExtraProperties SetDefaultsForExtraProperties(Type objectType){

     return this;
 }

}
