package com.ueueo.data.objectextending;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-23 13:39
 */
public class ExtensibleObject implements IHasExtraProperties, IValidatableObject{

    private final ExtraPropertyDictionary extraProperties;

    public ExtensibleObject() {
        this.extraProperties = new ExtraPropertyDictionary();
    }

    public ExtensibleObject(ExtraPropertyDictionary extraProperties) {
        this.extraProperties = extraProperties;
    }

    @Override
    public ExtraPropertyDictionary getExtraProperties() {
        return extraProperties;
    }
}
