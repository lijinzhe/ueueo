package com.ueueo.data.objectextending;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lee
 * @date 2022-05-23 13:41
 */
public class ExtraPropertyDictionary extends HashMap<String, Object> {

    public ExtraPropertyDictionary() {
    }

    public ExtraPropertyDictionary(Map<? extends String, ?> m) {
        super(m);
    }
}
