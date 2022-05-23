package com.ueueo.data.objectextending;

import com.ueueo.data.objectextending.modularity.ExtensionPropertyLookupConfiguration;
import com.ueueo.localization.IHasNameWithLocalizableDisplayName;
import com.ueueo.localization.ILocalizableString;
import lombok.Getter;
import lombok.Setter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-23 14:08
 */
@Getter
public class ObjectExtensionPropertyInfo implements IHasNameWithLocalizableDisplayName, IBasicObjectExtensionPropertyInfo {

    private ObjectExtensionInfo objectExtension;
    private String name;
    private Type type;

    private List<Annotation> attributes;
    private List<Consumer<ObjectExtensionPropertyValidationContext>> validators;

    private ILocalizableString displayName;

    private Boolean checkPairDefinitionOnMapping;

    private Map<Object, Object> configuration;

    @Setter
    private Object defaultValue;
    @Setter
    private Consumer<Object> defaultValueFactory;

    private ExtensionPropertyLookupConfiguration lookup;

    public ObjectExtensionPropertyInfo(ObjectExtensionInfo objectExtension, Type type, String name) {
        this.objectExtension = objectExtension;
        this.name = name;
        this.type = type;

        this.configuration = new HashMap<>();
        this.attributes = new ArrayList<>();
        this.validators = new ArrayList<>();

        //        Attributes.AddRange(ExtensionPropertyHelper.GetDefaultAttributes(Type));
        //        DefaultValue = TypeHelper.GetDefaultValue(Type);
        this.lookup = new ExtensionPropertyLookupConfiguration();
    }

    @Override
    public Object getDefaultValue() {
        //        return ExtensionPropertyHelper.GetDefaultValue(Type, DefaultValueFactory, DefaultValue);
        return null;
    }

}
