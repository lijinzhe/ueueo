package com.ueueo.data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lee
 * @date 2022-05-13 21:17
 */
public class ConnectionStrings extends HashMap<String, String> {

    public ConnectionStrings() {
    }

    public ConnectionStrings(Map<? extends String, ? extends String> m) {
        super(m);
    }

    public static final String DefaultConnectionStringName = "Default";

    public String getDefault() {
        return getOrDefault(DefaultConnectionStringName, "");
    }

    public void setDefault(String def) {
        put(DefaultConnectionStringName, def);
    }
}
