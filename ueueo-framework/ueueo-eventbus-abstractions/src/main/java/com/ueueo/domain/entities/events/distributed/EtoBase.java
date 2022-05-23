package com.ueueo.domain.entities.events.distributed;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Lee
 * @date 2022-05-16 10:54
 */
@Data
public abstract class EtoBase implements Serializable {
    protected Map<String, String> properties;

    public EtoBase() {
        this.properties = new HashMap<>();
    }
}
