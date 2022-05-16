package com.ueueo.data;

import java.util.HashMap;

/**
 * TODO Description Of This JAVA Class.
 *
 * @author Lee
 * @date 2022-05-13 21:17
 */
public class ConnectionStrings extends HashMap<String, String> {
    public static final String DefaultConnectionStringName = "Default";

    public String getDefault() {
        return getOrDefault(DefaultConnectionStringName, "");
    }

    public void setDefault(String def) {
        put(DefaultConnectionStringName, def);
    }
}
