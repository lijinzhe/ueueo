package com.ueueo.features;

import com.ueueo.SystemException;
import com.ueueo.localization.ILocalizableString;
import lombok.Getter;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lee
 * @date 2022-05-17 16:54
 */
public class FeatureDefinitionContext implements IFeatureDefinitionContext {
    @Getter
    private final Map<String, FeatureGroupDefinition> groups;

    public FeatureDefinitionContext() {
        this.groups = new HashMap<>();
    }

    @Override
    public FeatureGroupDefinition addGroup(String name, ILocalizableString displayName) {
        Assert.notNull(name, "name must not null.");
        if (groups.containsKey(name)) {
            throw new SystemException(String.format("There is already an existing feature group with name: %s", name));
        }
        FeatureGroupDefinition definition = new FeatureGroupDefinition(name, displayName);
        groups.put(name, definition);
        return definition;
    }

    @Override
    public FeatureGroupDefinition getGroupOrNull(String name) {
        Assert.notNull(name, "name must not null.");
        return groups.get(name);
    }

    @Override
    public void removeGroup(String name) {
        Assert.notNull(name, "name must not null.");
        if (groups.containsKey(name)) {
            groups.remove(name);
        } else {
            throw new SystemException(String.format("Undefined feature group: '%s'.", name));
        }
    }
}
