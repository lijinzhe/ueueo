package com.ueueo.data;

import com.ueueo.ID;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lee
 * @date 2022-05-20 16:24
 */
@Data
public class DataSeedContext {
    private final ID tenantId;
    private final Map<String, Object> properties;

    /**
     * Sets a key-value on the <see cref="Properties"/>.
     *
     * @param name  name Name of the property
     * @param value
     */
    public void setProperty(String name, Object value) {
        this.properties.put(name, value);
    }

    /**
     * Gets a key-value on the <see cref="Properties"/>.
     *
     * @param name Name of the property
     *
     * @return Returns the value in the <see cref="Properties"/> dictionary by given <paramref name="name"/>.
     * * Returns null if given <paramref name="name"/> is not present in the <see cref="Properties"/> dictionary.
     */
    public Object getProperty(String name) {
        return this.properties.get(name);
    }

    public DataSeedContext(ID tenantId) {
        this.tenantId = tenantId;
        this.properties = new HashMap<>();
    }

    /**
     * Sets a property in the <see cref="Properties"/> dictionary.
     * This is a shortcut for nested calls on this object.
     *
     * @param key
     * @param value
     *
     * @return
     */
    public DataSeedContext withProperty(String key, Object value) {
        this.properties.put(key, value);
        return this;
    }
}
